package im.wangbo.bj58.janus.transport.websocket;

import com.google.auto.value.AutoValue;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
abstract class SessionReq {
    abstract String getRequestType();

    abstract String getTransactionId();

    abstract long getSessionId();

    abstract JsonObject getMessage();

    public static Builder builder() {
        return new AutoValue_SessionReq.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setRequestType(String newRequestType);

        public abstract Builder setTransactionId(String newTransactionId);

        public abstract Builder setSessionId(long newSessionId);

        public abstract Builder setMessage(JsonObject newMessage);

        public abstract SessionReq build();
    }
}
