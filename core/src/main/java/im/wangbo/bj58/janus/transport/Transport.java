package im.wangbo.bj58.janus.transport;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public interface Transport {
    ListenableFuture<Void> connect(final ResponseHandler messageHandler);

    ListenableFuture<Void> close();

    ListenableFuture<Void> send(final GlobalRequest request);

    ListenableFuture<Void> send(final SessionRequest request);

    ListenableFuture<Void> send(final PluginHandleRequest request);
}
