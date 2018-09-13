package it;

import com.google.common.util.concurrent.ListenableFuture;

import org.junit.Test;

import java.net.URI;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import im.wangbo.bj58.janus.PluginHandle;
import im.wangbo.bj58.janus.Session;
import im.wangbo.bj58.janus.protocol.AsyncProtocol;
import im.wangbo.bj58.janus.ServerInfo;
import im.wangbo.bj58.janus.protocol.StdAsyncProtocol;
import im.wangbo.bj58.janus.transport.Transport;
import im.wangbo.bj58.janus.transport.websocket.WebSocketsTransport;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class JanusIT {
    @Test
    public void testing() throws Exception {
        final Transport transport = WebSocketsTransport.create(
//                new URI("wss://janus.conf.meetecho.com:8188/janus")
            new URI("ws://192.168.183.25:8888")
        );
        final AsyncProtocol protocol = StdAsyncProtocol.create(transport);

        protocol.connect();

        final ListenableFuture<ServerInfo> info = protocol.info();
        System.out.println("Janus info: " + info.get(5, TimeUnit.SECONDS));

        final ListenableFuture<Session> sessionFuture = protocol.create();
        final Session session = sessionFuture.get(5, TimeUnit.SECONDS);
        System.out.println("Session create: " + session);

        final ListenableFuture<PluginHandle> attachFuture = protocol.attach(session, "janus.plugin.echotest");
        final PluginHandle pluginHandle = attachFuture.get(5, TimeUnit.SECONDS);
        System.out.println("Plugin attach: " + pluginHandle);

        for (int i = 0; i < 10; i++) {
            final ListenableFuture<Void> keepaliveFuture = protocol.keepAlive(session);
            keepaliveFuture.get(5, TimeUnit.SECONDS);
        }

        final ListenableFuture<Void> trickleFuture = protocol.trickle(pluginHandle, Collections.emptyList());
        trickleFuture.get(5, TimeUnit.SECONDS);

        final ListenableFuture<Void> detachFuture = protocol.detach(pluginHandle);
        detachFuture.get(5, TimeUnit.SECONDS);

        final ListenableFuture<Void> destroyFuture = protocol.destroy(session);
        destroyFuture.get(5, TimeUnit.SECONDS);

        protocol.close();
    }
}
