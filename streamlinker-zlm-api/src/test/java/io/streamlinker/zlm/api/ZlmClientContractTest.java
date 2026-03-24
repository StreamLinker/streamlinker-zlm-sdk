package io.streamlinker.zlm.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.streamlinker.zlm.model.response.MediaInfoResponse;
import io.streamlinker.zlm.model.response.SimpleResultResponse;
import io.streamlinker.zlm.model.response.StreamKeyResponse;
import io.streamlinker.zlm.model.response.StreamUrlResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZlmClientContractTest {

    private MockWebServer server;
    private ZlmClient zlmClient;

    @BeforeEach
    void setUp() throws Exception {
        server = new MockWebServer();
        server.start();
        RestClient restClient = RestClient.builder()
                .baseUrl(server.url("/").toString())
                .build();
        zlmClient = new DefaultZlmClient(restClient, new ObjectMapper(), "test-secret");
    }

    @AfterEach
    void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    void shouldCallAddFfmpegSource() throws Exception {
        server.enqueue(json("{" +
                "\"code\":0,\"msg\":\"success\",\"data\":{\"key\":\"ffmpeg-1\",\"local_port\":1935}" +
                "}"));

        StreamKeyResponse response = zlmClient.addFfmpegSource("rtsp://a", "rtmp://b", 15000, false, false, null);
        String path = server.takeRequest().getPath();

        assertEquals("ffmpeg-1", response.getKey());
        assertTrue(path.startsWith("/index/api/addFFmpegSource?"));
        assertTrue(path.contains("secret=test-secret"));
        assertTrue(path.contains("src_url="));
        assertTrue(path.contains("dst_url="));
        assertTrue(path.contains("timeout_ms=15000"));
    }

    @Test
    void shouldCallAddStreamProxy() throws Exception {
        server.enqueue(json("{" +
                "\"code\":0,\"msg\":\"success\",\"data\":{\"key\":\"proxy-1\"}" +
                "}"));

        StreamKeyResponse response = zlmClient.addStreamProxy("live", "cam01", "rtsp://camera", 0, false, true);
        String path = server.takeRequest().getPath();

        assertEquals("proxy-1", response.getKey());
        assertTrue(path.startsWith("/index/api/addStreamProxy?"));
        assertTrue(path.contains("app=live"));
        assertTrue(path.contains("stream=cam01"));
        assertTrue(path.contains("url="));
    }

    @Test
    void shouldCallAddStreamPusherProxy() throws Exception {
        server.enqueue(json("{" +
                "\"code\":0,\"msg\":\"success\",\"data\":{\"key\":\"push-1\"}" +
                "}"));

        StreamKeyResponse response = zlmClient.addStreamPusherProxy("live", "cam01", "rtmp", "rtmp://cloud/live/cam01", 0);
        String path = server.takeRequest().getPath();

        assertEquals("push-1", response.getKey());
        assertTrue(path.startsWith("/index/api/addStreamPusherProxy?"));
        assertTrue(path.contains("schema=rtmp"));
        assertTrue(path.contains("dst_url="));
    }

    @Test
    void shouldCallDeleteApis() throws Exception {
        server.enqueue(json("{" +
                "\"code\":0,\"msg\":\"success\",\"data\":{\"flag\":true}" +
                "}"));
        server.enqueue(json("{" +
                "\"code\":0,\"msg\":\"success\",\"data\":{\"flag\":true}" +
                "}"));
        server.enqueue(json("{" +
                "\"code\":0,\"msg\":\"success\",\"data\":{\"flag\":true}" +
                "}"));

        SimpleResultResponse ffmpeg = zlmClient.delFfmpegSource("ffmpeg-1");
        SimpleResultResponse proxy = zlmClient.delStreamProxy("proxy-1");
        SimpleResultResponse pusher = zlmClient.delStreamPusherProxy("push-1");

        assertTrue(ffmpeg.getFlag());
        assertTrue(proxy.getFlag());
        assertTrue(pusher.getFlag());
        assertTrue(server.takeRequest().getPath().startsWith("/index/api/delFFmpegSource?"));
        assertTrue(server.takeRequest().getPath().startsWith("/index/api/delStreamProxy?"));
        assertTrue(server.takeRequest().getPath().startsWith("/index/api/delStreamPusherProxy?"));
    }

    @Test
    void shouldCallGetMediaInfo() throws Exception {
        server.enqueue(json("{" +
                "\"code\":0,\"msg\":\"success\",\"data\":{\"app\":\"live\",\"stream\":\"cam01\",\"schema\":\"rtmp\",\"origin_type\":7,\"reader_count\":2}" +
                "}"));

        MediaInfoResponse response = zlmClient.getMediaInfo("rtmp", "__defaultVhost__", "live", "cam01");
        String path = server.takeRequest().getPath();

        assertEquals("cam01", response.getStream());
        assertEquals(2, response.getReaderCount());
        assertTrue(path.startsWith("/index/api/getMediaInfo?"));
        assertTrue(path.contains("vhost=__defaultVhost__"));
    }

    @Test
    void shouldCallGetStreamUrl() throws Exception {
        server.enqueue(json("{" +
                "\"code\":0,\"msg\":\"success\",\"data\":{\"rtmp_url\":\"rtmp://cloud/live/cam01\",\"hls_url\":\"http://cloud/live/cam01/hls.m3u8\"}" +
                "}"));

        StreamUrlResponse response = zlmClient.getStreamUrl("rtmp", "__defaultVhost__", "live", "cam01");
        String path = server.takeRequest().getPath();

        assertEquals("rtmp://cloud/live/cam01", response.getRtmpUrl());
        assertTrue(path.startsWith("/index/api/getStreamUrl?"));
        assertTrue(path.contains("stream=cam01"));
    }

    @Test
    void shouldCallCloseStream() throws Exception {
        server.enqueue(json("{" +
                "\"code\":0,\"msg\":\"success\",\"data\":{\"flag\":true}" +
                "}"));

        SimpleResultResponse response = zlmClient.closeStream("rtmp", "__defaultVhost__", "live", "cam01", true);
        String path = server.takeRequest().getPath();

        assertEquals(true, response.getFlag());
        assertTrue(path.startsWith("/index/api/close_stream?"));
        assertTrue(path.contains("force=true"));
    }

    @Test
    void shouldThrowWhenZlmReturnsError() {
        server.enqueue(json("{" +
                "\"code\":-1,\"msg\":\"bad request\",\"data\":null" +
                "}"));

        assertThrows(ZlmClientException.class, () -> zlmClient.getMediaInfo("rtmp", "__defaultVhost__", "live", "cam01"));
    }

    private MockResponse json(String body) {
        return new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(body);
    }
}