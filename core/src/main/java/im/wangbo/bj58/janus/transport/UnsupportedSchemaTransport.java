package im.wangbo.bj58.janus.transport;

import com.google.common.util.concurrent.ListenableFuture;

import java.net.URI;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public final class UnsupportedSchemaTransport implements Transport {
    private final URI uri;

    public UnsupportedSchemaTransport(final URI uri) {
        this.uri = uri;
    }

    @Override
    public ListenableFuture<Void> connect(ResponseHandler messageHandler) {
        throw new UnsupportedOperationException("Unsupported uri schema: \"" + uri + "\"");
    }

    @Override
    public ListenableFuture<Void> close() {
        throw new UnsupportedOperationException("Unsupported uri schema: \"" + uri + "\"");
    }

    @Override
    public ListenableFuture<Void> send(final GlobalRequest request) {
        throw new UnsupportedOperationException("Unsupported uri schema: \"" + uri + "\"");
    }

    @Override
    public ListenableFuture<Void> send(final SessionRequest request) {
        throw new UnsupportedOperationException("Unsupported uri schema: \"" + uri + "\"");
    }

    @Override
    public ListenableFuture<Void> send(final PluginHandleRequest request) {
        throw new UnsupportedOperationException("Unsupported uri schema: \"" + uri + "\"");
    }
}
