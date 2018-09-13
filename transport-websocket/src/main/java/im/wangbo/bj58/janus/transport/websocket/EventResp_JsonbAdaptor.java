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
final class EventResp_JsonbAdaptor implements JsonbAdapter<EventResp, JsonObject> {
    @Override
    public JsonObject adaptToJson(final EventResp obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public EventResp adaptFromJson(final JsonObject obj) throws Exception {
        final JsonObject inner = obj.getJsonObject("pluginData");
        return EventResp.builder()
                .setTransactionId(obj.getString("transaction"))
                .setPluginId(obj.getJsonNumber("sender").longValue())
                .setPluginData(EventResp.PluginDataEntity.create(inner.getString("plugin")))
                .build();
    }
}
