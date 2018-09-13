package im.wangbo.bj58.janus.transport;

import com.google.auto.value.AutoValue;

import im.wangbo.bj58.janus.PluginHandle;
import im.wangbo.bj58.janus.Session;
import im.wangbo.bj58.janus.Transaction;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class WebrtcMediaResponse implements Response {
    public abstract Transaction transaction();
    public abstract PluginHandle pluginHandle();
    public abstract String mediaType();
    public abstract boolean isReceiving();

    public static Builder builder() {
        return new AutoValue_WebrtcMediaResponse.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder transaction(Transaction transaction);

        public abstract Builder pluginHandle(PluginHandle pluginHandle);

        public abstract Builder mediaType(String type);

        public abstract Builder isReceiving(boolean receiving);

        public abstract WebrtcMediaResponse build();
    }
}
