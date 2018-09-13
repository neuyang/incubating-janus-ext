package im.wangbo.bj58.janus.transport.websocket;

import com.google.common.collect.ImmutableList;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import javax.json.Json;
import javax.json.bind.annotation.JsonbProperty;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@Ignore
public class PluginHandleReqTest {
    @Test
    public void test_nullBodyNullJsepEmptyCandidate() {
        final PluginHandleReq req = PluginHandleReq.builder()
                .setRequestType("test")
                .setTransactionId("transactionId")
                .setSessionId(1000L)
                .setPluginHandleId(2000L)
                .setBody(null)
                .setJsep(null)
                .build();

        final String json = Jsons.toJson(
                req, PluginHandleReq.class
        );

        System.out.println(json);
    }

    private static final Random RANDOM = new Random();

    private static final class Entity {
        @JsonbProperty("janus")
        private String fieldA = "String " + RANDOM.nextLong();

        @JsonbProperty("transaction")
        private Boolean fieldB = RANDOM.nextBoolean();

        @JsonbProperty("change")
        private Integer fieldC = RANDOM.nextInt();

        @JsonbProperty("value")
        private Long fieldD = RANDOM.nextLong();
    }

    @Test
    public void test_uonullBodyNullJsepEmptyCandidate() {
        final Entity entity = new Entity();
        final PluginHandleReq req = PluginHandleReq.builder()
                .setRequestType("test")
                .setTransactionId("transactionId")
                .setSessionId(1000L)
                .setPluginHandleId(2000L)
                .setBody(
                        Json.createObjectBuilder()
                                .add("janus", entity.fieldA)
                                .add("transaction", entity.fieldB)
                                .add("change", entity.fieldC)
                                .add("value", entity.fieldD)
                                .build()
                )
                .setJsep(null)
                .build();

        final String json = Jsons.toJson(req, PluginHandleReq.class);

        System.out.println(json);
    }

    @Test
    public void test_nullBodyNonnullJsepEmptyCandidate() {
        final PluginHandleReq req = PluginHandleReq.builder()
                .setRequestType("test")
                .setTransactionId("transactionId")
                .setSessionId(1000L)
                .setPluginHandleId(2000L)
                .setBody(null)
                .setJsep(PluginHandleReqJsepSub.answer().setSdp("sdp sdp sdp sdp sdp").build())
                .build();

        final String json = Jsons.toJson(req, PluginHandleReq.class);

        System.out.println(json);
    }

    @Test
    public void test_nullBodyNullJsep1Candidate() {
        final PluginHandleReq req = PluginHandleReq.builder()
                .setRequestType("test")
                .setTransactionId("transactionId")
                .setSessionId(1000L)
                .setPluginHandleId(2000L)
                .setBody(null)
                .setJsep(null)
                .setCandidate(PluginHandleReqCandidateSub.completed())
                .build();

        final String json = Jsons.toJson(req, PluginHandleReq.class);

        System.out.println(json);
    }

    @Test
    public void test_nullBodyNullJsepCandidate2() {
        final PluginHandleReq req = PluginHandleReq.builder()
                .setRequestType("test")
                .setTransactionId("transactionId")
                .setSessionId(1000L)
                .setPluginHandleId(2000L)
                .setBody(null)
                .setJsep(null)
                .setCandidates(
                        ImmutableList.of(PluginHandleReqCandidateSub.candidate(
                                "sdp mid 1", 1, "candidate 1"
                                ),
                                PluginHandleReqCandidateSub.candidate(
                                        "sdp mid 2", 2, "candidate 2"
                                )
                        )
                )
                .build();

        final String json = Jsons.toJson(req, PluginHandleReq.class);

        System.out.println(json);
    }
}