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
abstract class GlobalReq {
    abstract String getRequestType();

    abstract String getTransactionId();

    public static Builder builder() {
        return new AutoValue_GlobalReq.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setRequestType(String newRequestType);

        public abstract Builder setTransactionId(String newTransactionId);

        public abstract GlobalReq build();
    }
}
