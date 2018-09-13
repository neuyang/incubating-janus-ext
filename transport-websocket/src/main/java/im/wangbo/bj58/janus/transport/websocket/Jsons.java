package im.wangbo.bj58.janus.transport.websocket;

import java.util.Optional;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
final class Jsons {
    private Jsons() { throw new AssertionError("Construction forbidden"); }

    private static final Jsonb jsonb = JsonbBuilder.create(
            new JsonbConfig().withAdapters(
                    new GlobalReq_JsonbAdaptor(),
                    new SessionReq_JsonbAdaptor(),
                    new PluginHandleReq_JsonbAdaptor(),
                    new TypeResp_JsonbAdaptor(),
                    new AckResp_JsonbAdaptor(),
                    new ErrorResp_JsonbAdaptor(),
                    new EventResp_JsonbAdaptor(),
                    new HangupResp_JsonbAdaptor(),
                    new MediaResp_JsonbAdaptor(),
                    new ServerInfoResp_JsonbAdaptor(),
                    new SlowlinkResp_JsonbAdaptor(),
                    new SuccessResp_JsonbAdaptor(),
                    new WebrtcUpResp_JsonbAdaptor()
            )
    );

    static <T> Optional<T> fromJson(final String json, final Class<T> clz) {
        try {
            return Optional.ofNullable(jsonb.fromJson(json, clz));
        } catch (Exception e) { // TODO
            return Optional.empty();
        }
    }

    static <T> String toJson(final T req, final Class<T> clz) {
        return jsonb.toJson(req, clz);
    }
}
