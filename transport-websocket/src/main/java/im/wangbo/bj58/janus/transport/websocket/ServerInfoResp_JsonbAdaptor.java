package im.wangbo.bj58.janus.transport.websocket;

import com.google.common.collect.ImmutableMap;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
final class ServerInfoResp_JsonbAdaptor implements JsonbAdapter<ServerInfoResp, JsonObject> {
    @Override
    public JsonObject adaptToJson(final ServerInfoResp obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public ServerInfoResp adaptFromJson(final JsonObject obj) throws Exception {
        final ImmutableMap.Builder<String, ServerInfoResp.PluginDesc> pluginsBuilder = ImmutableMap.builder();
        {
            final JsonObject inner = obj.getJsonObject("plugins");
            inner.forEach(
                    (k, v) -> {
                        final JsonObject o = (JsonObject) v;
                        pluginsBuilder.put(
                                k,
                                ServerInfoResp.PluginDesc.builder()
                                        .setName(o.getString("name"))
                                        .setAuthor(o.getString("author"))
                                        .setVersion(o.getInt("version"))
                                        .setVersionDescription(o.getString("version_string"))
                                        .setDescription(o.getString("description"))
                                        .build()
                        );
                    }
            );
        }
        final ImmutableMap.Builder<String, ServerInfoResp.TransportDesc> transportsBuilder = ImmutableMap.builder();
        {
            final JsonObject inner = obj.getJsonObject("transports");
            inner.forEach(
                    (k, v) -> {
                        final JsonObject o = (JsonObject) v;
                        transportsBuilder.put(
                                k,
                                ServerInfoResp.TransportDesc.builder()
                                        .setName(o.getString("name"))
                                        .setAuthor(o.getString("author"))
                                        .setVersion(o.getInt("version"))
                                        .setVersionDescription(o.getString("version_string"))
                                        .setDescription(o.getString("description"))
                                        .build()
                        );
                    }
            );
        }
        final ImmutableMap.Builder<String, ServerInfoResp.EvHandlerDesc> evhandlersBuilder = ImmutableMap.builder();
        {
            final JsonObject inner = obj.getJsonObject("events");
            inner.forEach(
                    (k, v) -> {
                        final JsonObject o = (JsonObject) v;
                        evhandlersBuilder.put(
                                k,
                                ServerInfoResp.EvHandlerDesc.builder()
                                        .setName(o.getString("name"))
                                        .setAuthor(o.getString("author"))
                                        .setVersion(o.getInt("version"))
                                        .setVersionDescription(o.getString("version_string"))
                                        .setDescription(o.getString("description"))
                                        .build()
                        );
                    }
            );
        }
        return ServerInfoResp.builder()
                .setTransactionId(obj.getString("transaction"))
                .setName(obj.getString("name"))
                .setAuthor(obj.getString("author"))
                .setVersion(obj.getInt("version"))
                .setVersionDescription(obj.getString("version_string"))
                .setSessionTimeout(obj.getInt("session-timeout"))
                .setPlugins(pluginsBuilder.build())
                .setTransports(transportsBuilder.build())
                .setEventHandlers(evhandlersBuilder.build())
                .build();
    }
}
