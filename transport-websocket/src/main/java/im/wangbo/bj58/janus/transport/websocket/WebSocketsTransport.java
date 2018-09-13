package im.wangbo.bj58.janus.transport.websocket;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javax.annotation.Nullable;
import javax.json.JsonObject;

import im.wangbo.bj58.janus.Candidate;
import im.wangbo.bj58.janus.Jsep;
import im.wangbo.bj58.janus.transport.GlobalRequest;
import im.wangbo.bj58.janus.transport.PluginHandleRequest;
import im.wangbo.bj58.janus.transport.ResponseHandler;
import im.wangbo.bj58.janus.transport.SessionRequest;
import im.wangbo.bj58.janus.transport.Transport;
import im.wangbo.bj58.janus.transport.UnsupportedSchemaTransport;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import static com.google.common.base.Preconditions.checkArgument;
import static im.wangbo.bj58.janus.transport.websocket.Jsons.toJson;
import static io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE;
import static io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_ISSUED;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class WebSocketsTransport implements Transport {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketsTransport.class);

    private static final String SCHEMA_WEBSOCKET = "ws";
    private static final String SCHEMA_WEBSOCKET_SECURE = "wss";

    private final URI janusUri;

    private EventLoopGroup workerGroup;

    /*
     * Optional#isPresent(): connected successful
     * Optional#isAbsent(): connected failed
     * null: connecting
     */
    @Nullable
    private volatile Optional<Channel> outBound = null;

    /*
     * Messages requested when connection process not finished should be cached,
     * so all messages could be sent after connection established.
     */
    private final Map<String, SettableFuture<Void>> cachedMessages = Maps.newConcurrentMap();

    private WebSocketsTransport(final URI janusUri) {
        this.janusUri = janusUri;
    }

    public static Transport create(final URI uri) {
        final String protocol = uri.getScheme();
        if (SCHEMA_WEBSOCKET.equalsIgnoreCase(protocol) || SCHEMA_WEBSOCKET_SECURE.equalsIgnoreCase(protocol)) {
            return new WebSocketsTransport(uri);
        } else {
            return new UnsupportedSchemaTransport(uri);
        }
    }

    @Override
    public ListenableFuture<Void> connect(
            final ResponseHandler messageHandler
    ) {
        checkArgument(null == workerGroup, "A connect attempt is being processed");

        final SettableFuture<Void> shakeHandPromise = SettableFuture.create();

        workerGroup = new NioEventLoopGroup();
        final Bootstrap b = new Bootstrap();

        final WebSocketClientReceiveHandler webSocketClientReceiveHandler =
                new WebSocketClientReceiveHandler(
                        new StdMessageHandler(messageHandler), shakeHandPromise
                );
        b.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new WebSocketChannelInitializer(janusUri, webSocketClientReceiveHandler));

        final SettableFuture<Void> promise = SettableFuture.create();
        b.connect(janusUri.getHost(), janusUri.getPort()).addListener(
                f -> {
                    final ChannelFuture cf = (ChannelFuture) f;
                    if (cf.isSuccess()) {
                        shakeHandPromise.addCallback(
                                new FutureCallback<Void>() {
                                    @Override
                                    public void onSuccess(@Nullable Void result) {
                                        final Channel ch = cf.channel();

                                        setOutBoundChannel(ch);

                                        promise.set(result);

                                        cachedMessages.forEach(
                                                (k, v) -> send(ch, k, v)
                                        );
                                        cachedMessages.clear();
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        outBound = Optional.empty();
                                        promise.setException(t);

                                        cachedMessages.forEach(
                                                (k, v) -> v.setException(t)
                                        );
                                        cachedMessages.clear();
                                    }
                                },
                                MoreExecutors.directExecutor()
                        );
                    } else {
                        setOutBoundChannel(null);

                        promise.setException(cf.cause());

                        cachedMessages.forEach(
                                (k, v) -> v.setException(cf.cause())
                        );
                        cachedMessages.clear();
                    }
                }
        );
        LOGGER.debug("Connecting requested to \"{}\"", janusUri);
        return promise;
    }

    @Override
    public ListenableFuture<Void> close() {
        final SettableFuture<Void> promise = SettableFuture.create();

        final Optional<Channel> outChannel = outBound;
        if (null != outChannel && outChannel.isPresent()) {
            outChannel.get().writeAndFlush(new CloseWebSocketFrame())
                    .addListener(ChannelFutureListener.CLOSE)
                    .addListener(
                            f -> workerGroup.shutdownGracefully().addListener(
                                    f2 -> {
                                        if (f2.isSuccess())
                                            promise.set(null);
                                        else
                                            promise.setException(f2.cause());
                                    }
                            )
                    );
        }
        cachedMessages.clear();

        LOGGER.debug("Closing requested to \"{}\"", janusUri);
        return promise;
    }

    @Override
    public ListenableFuture<Void> send(final GlobalRequest request) {
        final GlobalReq.Builder builder = GlobalReq.builder()
                .setRequestType(request.request())
                .setTransactionId(request.transaction().id());

        return send(toJson(builder.build(), GlobalReq.class));
    }

    @Override
    public ListenableFuture<Void> send(final SessionRequest request) {
        final SessionReq.Builder builder = SessionReq.builder()
                .setRequestType(request.request())
                .setSessionId(request.session().id())
                .setTransactionId(request.transaction().id())
                .setMessage(request.message());

        return send(toJson(builder.build(), SessionReq.class));
    }

    @Override
    public ListenableFuture<Void> send(final PluginHandleRequest request) {
        final PluginHandleReq.Builder builder = PluginHandleReq.builder()
                .setRequestType(request.request())
                .setSessionId(request.pluginHandle().session().id())
                .setPluginHandleId(request.pluginHandle().id())
                .setTransactionId(request.transaction().id());

        final List<Candidate> candidates = request.candidates();
        if (null != candidates) {
            builder.setCandidates(
                    candidates.stream()
                            .map(e -> PluginHandleReqCandidateSub.candidate(e.sdpMid(), e.sdpMlineIndex(), e.candidate()))
                            .collect(ImmutableList.toImmutableList())
            );
        }

        final Jsep jsep = request.jsep();
        if (null != jsep) {
            builder.setJsep(
                    PluginHandleReqJsepSub.builder()
                            .setType(jsep.type())
                            .setSdp(jsep.sdp())
                            .build()
            );
        }

        final JsonObject body = request.data();
        if (null != body) {
            builder.setBody(body);
        }

        return send(toJson(builder.build(), PluginHandleReq.class));
    }

    private ListenableFuture<Void> send(final String msg) {
        final SettableFuture<Void> promise = SettableFuture.create();
        if (null == outBound) {
            // WebSockets connecting, cache it
            cachedMessages.put(msg, promise);
            LOGGER.info("Cached direct msg: {}", msg);
        } else {
            if (outBound.isPresent()) {
                send(outBound.get(), msg, promise);
            } else {
                promise.setException(new Exception("Connecting failed"));
            }
        }
        return promise;
    }

    private void send(final Channel channel, final String msg, final SettableFuture<Void> promise) {
        channel.writeAndFlush(new TextWebSocketFrame(msg)).addListener(
                f -> {
                    if (f.isSuccess()) {
                        promise.set(null);
                    } else {
                        promise.setException(f.cause());
                    }
                }
        );
        LOGGER.debug("Sent msg: {}", msg);
    }

    private void setOutBoundChannel(final Channel channel) {
        outBound = Optional.ofNullable(channel);
    }

    static final class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
        private final URI uri;
        private final WebSocketClientReceiveHandler webSocketHandler;

        WebSocketChannelInitializer(
                final URI uri, final WebSocketClientReceiveHandler handler
        ) {
            this.uri = uri;
            this.webSocketHandler = handler;
        }

        @Override
        protected void initChannel(final SocketChannel ch) throws Exception {
            if (SCHEMA_WEBSOCKET_SECURE.equalsIgnoreCase(uri.getScheme())) {
                final SslContext sslContext = SslContextBuilder.forClient()
                        .trustManager(InsecureTrustManagerFactory.INSTANCE)
                        .build();
                ch.pipeline().addLast(sslContext.newHandler(ch.alloc(), uri.getHost(), uri.getPort()));
            }

            ch.pipeline()
                    .addLast("http-client", new HttpClientCodec())
                    .addLast("http-aggregator", new HttpObjectAggregator(65536))
                    .addLast("ws-handler", new WebSocketClientProtocolHandler(
                            uri, WebSocketVersion.V13, "janus-protocol",
                            true, new DefaultHttpHeaders(), 65535
                    ))
                    .addLast("logic-handler", webSocketHandler);
        }
    }

    static final class WebSocketClientReceiveHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        private final Consumer<String> messageHandler;
        private final SettableFuture<Void> handShakePromise;

        WebSocketClientReceiveHandler(
                final Consumer<String> msgHandler,
                final SettableFuture<Void> handShakePromise
        ) {
            super();

            this.handShakePromise = handShakePromise;
            this.messageHandler = msgHandler;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            LOGGER.debug("Connection active");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            LOGGER.debug("Connection inactive");
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
            final String text = msg.text();

            LOGGER.debug("Received from WebSocket: {}", text);
            messageHandler.accept(msg.text());
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt == HANDSHAKE_ISSUED) {
                LOGGER.debug("Hand shake started: {}", evt);
            } else if (evt == HANDSHAKE_COMPLETE) {
                LOGGER.debug("Hand shake finished: {}", evt);

                // To notify the hand-shake-upgrade phase finished
                handShakePromise.set(null);
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }
    }
}
