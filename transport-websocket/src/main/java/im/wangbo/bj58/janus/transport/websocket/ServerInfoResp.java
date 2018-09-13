package im.wangbo.bj58.janus.transport.websocket;

import com.google.auto.value.AutoValue;

import java.util.Map;

import javax.annotation.Nullable;
import javax.json.bind.annotation.JsonbProperty;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * TODO add more fields
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
abstract class ServerInfoResp {
    @AutoValue
    static abstract class PluginDesc {
        @JsonbProperty("name")
        private String name;
        @JsonbProperty("author")
        private String author;
        @JsonbProperty("version")
        private Integer version;
        @JsonbProperty("version_string")
        private String versionDescription;
        @JsonbProperty("description")
        private String description;

        abstract String getName();

        abstract String getAuthor();

        abstract Integer getVersion();

        abstract String getVersionDescription();

        abstract String getDescription();

        public static Builder builder() {
            return new AutoValue_ServerInfoResp_PluginDesc.Builder();
        }

        @AutoValue.Builder
        public abstract static class Builder {
            public abstract Builder setName(String newName);

            public abstract Builder setAuthor(String newAuthor);

            public abstract Builder setVersion(Integer newVersion);

            public abstract Builder setVersionDescription(String newVersionDescription);

            public abstract Builder setDescription(String newDescription);

            public abstract PluginDesc build();
        }
    }

    @AutoValue
    static abstract class TransportDesc {
        abstract String getName();

        abstract String getAuthor();

        abstract Integer getVersion();

        abstract String getVersionDescription();

        abstract String getDescription();

        public static Builder builder() {
            return new AutoValue_ServerInfoResp_TransportDesc.Builder();
        }

        @AutoValue.Builder
        public abstract static class Builder {
            public abstract Builder setName(String newName);

            public abstract Builder setAuthor(String newAuthor);

            public abstract Builder setVersion(Integer newVersion);

            public abstract Builder setVersionDescription(String newVersionDescription);

            public abstract Builder setDescription(String newDescription);

            public abstract TransportDesc build();
        }
    }

    @AutoValue
    static abstract class EvHandlerDesc {
        abstract String getName();

        abstract String getAuthor();

        abstract Integer getVersion();

        abstract String getVersionDescription();

        abstract String getDescription();

        public static Builder builder() {
            return new AutoValue_ServerInfoResp_EvHandlerDesc.Builder();
        }

        @AutoValue.Builder
        public abstract static class Builder {
            public abstract Builder setName(String newName);

            public abstract Builder setAuthor(String newAuthor);

            public abstract Builder setVersion(Integer newVersion);

            public abstract Builder setVersionDescription(String newVersionDescription);

            public abstract Builder setDescription(String newDescription);

            public abstract EvHandlerDesc build();
        }
    }

    abstract String getTransactionId();

    abstract String getName();

    abstract String getAuthor();

    abstract Integer getVersion();

    abstract String getVersionDescription();

    abstract Integer getSessionTimeout();

    @Nullable
    abstract Map<String, PluginDesc> getPlugins();

    @Nullable
    abstract Map<String, EvHandlerDesc> getEventHandlers();

    @Nullable
    abstract Map<String, TransportDesc> getTransports();

    public static Builder builder() {
        return new AutoValue_ServerInfoResp.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTransactionId(String newTransactionId);

        public abstract Builder setName(String newName);

        public abstract Builder setAuthor(String newAuthor);

        public abstract Builder setVersion(Integer newVersion);

        public abstract Builder setVersionDescription(String newVersionDescription);

        public abstract Builder setSessionTimeout(Integer newSessionTimeout);

        public abstract Builder setPlugins(Map<String, PluginDesc> newPlugins);

        public abstract Builder setEventHandlers(Map<String, EvHandlerDesc> newEventHandlers);

        public abstract Builder setTransports(Map<String, TransportDesc> newTransports);

        public abstract ServerInfoResp build();
    }
}
