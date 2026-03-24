package io.streamlinker.zlm.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.streamlinker.zlm.model.request.AddFfmpegSourceRequest;
import io.streamlinker.zlm.model.response.StreamKeyResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZlmModelSerializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldSerializeFfmpegRequestWithOfficialFieldNames() throws Exception {
        AddFfmpegSourceRequest request = AddFfmpegSourceRequest.builder()
                .srcUrl("rtsp://camera/live")
                .dstUrl("rtmp://cloud/live/cam01")
                .timeoutMs(15000)
                .enableHls(false)
                .enableMp4(false)
                .ffmpegCmdKey("default")
                .build();

        String json = objectMapper.writeValueAsString(request);

        assertTrue(json.contains("\"src_url\":\"rtsp://camera/live\""));
        assertTrue(json.contains("\"dst_url\":\"rtmp://cloud/live/cam01\""));
        assertTrue(json.contains("\"timeout_ms\":15000"));
        assertTrue(json.contains("\"ffmpeg_cmd_key\":\"default\""));
    }

    @Test
    void shouldDeserializeGenericResponseWrapper() throws Exception {
        String json = """
                {
                  \"code\": 0,
                  \"msg\": \"success\",
                  \"data\": {
                    \"key\": \"task-1\",
                    \"local_port\": 1935
                  }
                }
                """;

        ZlmResponse<StreamKeyResponse> response = objectMapper.readValue(
                json,
                objectMapper.getTypeFactory().constructParametricType(ZlmResponse.class, StreamKeyResponse.class)
        );

        assertEquals(0, response.getCode());
        assertEquals("success", response.getMsg());
        assertEquals("task-1", response.getData().getKey());
        assertEquals(1935, response.getData().getLocalPort());
    }
}
