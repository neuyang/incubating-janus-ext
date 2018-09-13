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
final class SuccessResp_JsonbAdaptor implements JsonbAdapter<SuccessResp, JsonObject> {
    @Override
    public JsonObject adaptToJson(final SuccessResp obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public SuccessResp adaptFromJson(final JsonObject obj) throws Exception {
        final JsonObject inner = obj.getJsonObject("data");
        return SuccessResp.builder()
                .setTransactionId(obj.getString("transaction"))
                .setData(null != inner ? inner : JsonObject.EMPTY_JSON_OBJECT)
                .build();
    }
}
