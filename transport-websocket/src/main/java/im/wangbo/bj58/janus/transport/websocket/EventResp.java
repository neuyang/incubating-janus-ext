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
abstract class EventResp {
    abstract String getTransactionId();

    abstract Long getPluginId();

    abstract PluginDataEntity getPluginData();

    public static Builder builder() {
        return new AutoValue_EventResp.Builder();
    }

    @AutoValue
    static abstract class PluginDataEntity {
        abstract String getPluginName();

        public static PluginDataEntity create(String newPluginName) {
            return new AutoValue_EventResp_PluginDataEntity(newPluginName);
        }
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTransactionId(String newTransactionId);

        public abstract Builder setPluginId(Long newPluginId);

        public abstract Builder setPluginData(PluginDataEntity newPluginData);

        public abstract EventResp build();
    }
}
