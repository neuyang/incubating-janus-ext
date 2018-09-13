package im.wangbo.bj58.janus.transport.websocket;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
final class PluginHandleReq_JsonbAdaptor implements JsonbAdapter<PluginHandleReq, JsonObject> {
    @Override
    public JsonObject adaptToJson(PluginHandleReq obj) throws Exception {
        final JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("janus", obj.getRequestType())
                .add("transaction", obj.getTransactionId())
                .add("session_id", obj.getSessionId())
                .add("handle_id", obj.getPluginHandleId());

        if (null != obj.getBody()) {
            builder.add("body", obj.getBody());
        }

        if (null != obj.getJsep()) {
            final PluginHandleReqJsepSub sub = obj.getJsep();
            final JsonObjectBuilder inner = Json.createObjectBuilder()
                    .add("type", sub.getType())
                    .add("sdp", sub.getSdp());

            if (null != sub.getTrickle()) {
                inner.add("trickle", sub.getTrickle());
            }

            builder.add("jsep", inner.build());
        }

        if (null != obj.getCandidates()) {
            final List<PluginHandleReqCandidateSub> subs = obj.getCandidates();
            if (1 == subs.size()) {
                builder.add("candidate", createJsonObject(subs.get(0)));
            } if (subs.size() > 1) {
                final JsonArrayBuilder inner = Json.createArrayBuilder();
                subs.forEach(e -> inner.add(createJsonObject(e)));
                builder.add("candidates", inner.build());
            }
        } else if (null != obj.getCandidate()) {
            builder.add("candidate", createJsonObject(obj.getCandidate()));
        }

        return builder.build();
    }

    private JsonObject createJsonObject(final PluginHandleReqCandidateSub sub) {
        final JsonObjectBuilder inner = Json.createObjectBuilder();

        if (Boolean.TRUE.equals(sub.getCompleted())) {
            inner.add("completed", Boolean.TRUE);
        } else {
            inner.add("sdpMid", sub.getSdpMid())
                    .add("sdpMLineIndex", sub.getSdpMlineIndex())
                    .add("candidate", sub.getCandidate());
        }
        return inner.build();
    }

    @Override
    public PluginHandleReq adaptFromJson(JsonObject jsonObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet");
    }
}
