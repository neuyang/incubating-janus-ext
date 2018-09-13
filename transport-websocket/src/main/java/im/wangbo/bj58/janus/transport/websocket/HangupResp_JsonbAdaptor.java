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
final class HangupResp_JsonbAdaptor implements JsonbAdapter<HangupResp, JsonObject> {
    @Override
    public JsonObject adaptToJson(final HangupResp obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public HangupResp adaptFromJson(final JsonObject obj) throws Exception {
        return HangupResp.builder()
                .setTransactionId(obj.getString("transaction"))
                .setSessionId(obj.getJsonNumber("session_id").longValue())
                .setPluginId(obj.getJsonNumber("sender").longValue())
                .setMessage(obj.getString("reason"))
                .build();
    }
}
