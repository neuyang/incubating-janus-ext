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
final class WebrtcUpResp_JsonbAdaptor implements JsonbAdapter<WebrtcUpResp, JsonObject> {
    @Override
    public JsonObject adaptToJson(final WebrtcUpResp obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public WebrtcUpResp adaptFromJson(final JsonObject obj) throws Exception {
        return WebrtcUpResp.builder()
                .setTransactionId(obj.getString("transaction"))
                .setSessionId(obj.getJsonNumber("session_id").longValue())
                .setPluginId(obj.getJsonNumber("sender").longValue())
                .build();
    }
}
