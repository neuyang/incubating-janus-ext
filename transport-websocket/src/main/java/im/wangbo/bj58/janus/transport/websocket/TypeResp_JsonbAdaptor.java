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
final class TypeResp_JsonbAdaptor implements JsonbAdapter<TypeResp, JsonObject> {
    @Override
    public JsonObject adaptToJson(final TypeResp obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public TypeResp adaptFromJson(final JsonObject obj) throws Exception {
        return TypeResp.create(obj.getString("janus"));
    }
}
