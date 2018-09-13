package im.wangbo.bj58.janus.transport.websocket;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
final class GlobalReq_JsonbAdaptor implements JsonbAdapter<GlobalReq, JsonObject> {
    @Override
    public JsonObject adaptToJson(final GlobalReq obj) throws Exception {
        return Json.createObjectBuilder()
                .add("janus", obj.getRequestType())
                .add("transaction", obj.getTransactionId())
                .build();
    }

    @Override
    public GlobalReq adaptFromJson(final JsonObject obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet");
    }
}
