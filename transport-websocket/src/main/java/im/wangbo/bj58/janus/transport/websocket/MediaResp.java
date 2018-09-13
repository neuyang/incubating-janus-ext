package im.wangbo.bj58.janus.transport.websocket;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
abstract class MediaResp {
    abstract String getTransactionId();

    abstract Long getSessionId();

    abstract Long getPluginId();

    abstract String getMediaType();

    abstract Boolean getReceiving();

    public static Builder builder() {
        return new AutoValue_MediaResp.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTransactionId(String newTransactionId);

        public abstract Builder setSessionId(Long newSessionId);

        public abstract Builder setPluginId(Long newPluginId);

        public abstract Builder setMediaType(String newMediaType);

        public abstract Builder setReceiving(Boolean newReceiving);

        public abstract MediaResp build();
    }
}
