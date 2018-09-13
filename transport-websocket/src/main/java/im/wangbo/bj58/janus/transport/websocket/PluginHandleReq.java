package im.wangbo.bj58.janus.transport.websocket;

import com.google.auto.value.AutoValue;

import java.util.List;

import javax.annotation.Nullable;
import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
abstract class PluginHandleReq {
    abstract String getRequestType();

    abstract String getTransactionId();

    abstract long getSessionId();

    abstract long getPluginHandleId();

    @Nullable
    abstract JsonObject getBody();

    @Nullable
    abstract PluginHandleReqJsepSub getJsep();

    @Nullable
    // For trickle
    abstract PluginHandleReqCandidateSub getCandidate();

    @Nullable
    // For trickle
    abstract List<PluginHandleReqCandidateSub> getCandidates();

    public static Builder builder() {
        return new AutoValue_PluginHandleReq.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setRequestType(String newRequestType);

        public abstract Builder setTransactionId(String newTransactionId);

        public abstract Builder setSessionId(long newSessionId);

        public abstract Builder setPluginHandleId(long newPluginHandleId);

        public abstract Builder setBody(JsonObject newBody);

        public abstract Builder setJsep(PluginHandleReqJsepSub newJsep);

        public abstract Builder setCandidate(PluginHandleReqCandidateSub newCandidate);

        public abstract Builder setCandidates(List<PluginHandleReqCandidateSub> newCandidates);

        public abstract PluginHandleReq build();
    }
}
