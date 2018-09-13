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
abstract class ErrorResp {
    abstract String getTransactionId();

    abstract ErrorEntity getError();

    public static Builder builder() {
        return new AutoValue_ErrorResp.Builder();
    }

    @AutoValue
    static abstract class ErrorEntity {
        abstract int getCode();

        abstract String getMessage();

        public static ErrorEntity create(int newCode, String newMessage) {
            return new AutoValue_ErrorResp_ErrorEntity(newCode, newMessage);
        }
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTransactionId(String newTransactionId);

        public abstract Builder setError(ErrorEntity newError);

        public abstract ErrorResp build();
    }
}

