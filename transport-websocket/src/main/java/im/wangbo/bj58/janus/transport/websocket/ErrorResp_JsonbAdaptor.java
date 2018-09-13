package im.wangbo.bj58.janus.transport.websocket;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
final class ErrorResp_JsonbAdaptor implements JsonbAdapter<ErrorResp, JsonObject> {
    @Override
    public JsonObject adaptToJson(final ErrorResp obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public ErrorResp adaptFromJson(final JsonObject obj) throws Exception {
        final JsonObject inner = obj.getJsonObject("error");
        return ErrorResp.builder()
                .setTransactionId(obj.getString("transaction"))
                .setError(ErrorResp.ErrorEntity.create(inner.getInt("code"), inner.getString("reason")))
                .build();
    }
}
