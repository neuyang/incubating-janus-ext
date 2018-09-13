package im.wangbo.bj58.janus.transport.websocket;

import org.junit.Ignore;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@Ignore
public class JsonsTest {
    @Test
    public void jsonObject() {
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("a", "AAAA");
        builder.add("b", 1000);
        builder.add("c", "CCCCC");

        final String json = Jsons.toJson(builder.build(), JsonObject.class);

        System.out.println(json);
    }
}