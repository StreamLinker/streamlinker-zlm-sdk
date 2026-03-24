package io.streamlinker.zlm.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.streamlinker.zlm.model.ZlmResponse;
import io.streamlinker.zlm.model.response.MediaInfoResponse;
import io.streamlinker.zlm.model.response.SimpleResultResponse;
import io.streamlinker.zlm.model.response.StreamKeyResponse;
import io.streamlinker.zlm.model.response.StreamUrlResponse;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultZlmClient implements ZlmClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final String secret;

    public DefaultZlmClient(RestClient restClient, ObjectMapper objectMapper, String secret) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.secret = secret;
    }

    @Override
    public StreamKeyResponse addFfmpegSource(String srcUrl, String dstUrl, Integer timeoutMs, Boolean enableHls, Boolean enableMp4, String ffmpegCmdKey) {
        Map<String, Object> params = orderedParams();
        params.put("src_url", srcUrl);
        params.put("dst_url", dstUrl);
        params.put("timeout_ms", timeoutMs);
        params.put("enable_hls", enableHls);
        params.put("enable_mp4", enableMp4);
        params.put("ffmpeg_cmd_key", ffmpegCmdKey);
        return get("/index/api/addFFmpegSource", params, new TypeReference<>() {});
    }

    @Override
    public StreamKeyResponse addStreamProxy(String app, String stream, String url, Integer rtpType, Boolean enableMp4, Boolean autoClose) {
        Map<String, Object> params = orderedParams();
        params.put("app", app);
        params.put("stream", stream);
        params.put("url", url);
        params.put("rtp_type", rtpType);
        params.put("enable_mp4", enableMp4);
        params.put("auto_close", autoClose);
        return get("/index/api/addStreamProxy", params, new TypeReference<>() {});
    }

    @Override
    public StreamKeyResponse addStreamPusherProxy(String app, String stream, String schema, String dstUrl, Integer rtpType) {
        Map<String, Object> params = orderedParams();
        params.put("app", app);
        params.put("stream", stream);
        params.put("schema", schema);
        params.put("dst_url", dstUrl);
        params.put("rtp_type", rtpType);
        return get("/index/api/addStreamPusherProxy", params, new TypeReference<>() {});
    }

    @Override
    public SimpleResultResponse delFfmpegSource(String key) {
        Map<String, Object> params = orderedParams();
        params.put("key", key);
        return get("/index/api/delFFmpegSource", params, new TypeReference<>() {});
    }

    @Override
    public SimpleResultResponse delStreamProxy(String key) {
        Map<String, Object> params = orderedParams();
        params.put("key", key);
        return get("/index/api/delStreamProxy", params, new TypeReference<>() {});
    }

    @Override
    public SimpleResultResponse delStreamPusherProxy(String key) {
        Map<String, Object> params = orderedParams();
        params.put("key", key);
        return get("/index/api/delStreamPusherProxy", params, new TypeReference<>() {});
    }

    @Override
    public MediaInfoResponse getMediaInfo(String schema, String vhost, String app, String stream) {
        Map<String, Object> params = orderedParams();
        params.put("schema", schema);
        params.put("vhost", vhost);
        params.put("app", app);
        params.put("stream", stream);
        return get("/index/api/getMediaInfo", params, new TypeReference<>() {});
    }

    @Override
    public StreamUrlResponse getStreamUrl(String schema, String vhost, String app, String stream) {
        Map<String, Object> params = orderedParams();
        params.put("schema", schema);
        params.put("vhost", vhost);
        params.put("app", app);
        params.put("stream", stream);
        return get("/index/api/getStreamUrl", params, new TypeReference<>() {});
    }

    @Override
    public SimpleResultResponse closeStream(String schema, String vhost, String app, String stream, Boolean force) {
        Map<String, Object> params = orderedParams();
        params.put("schema", schema);
        params.put("vhost", vhost);
        params.put("app", app);
        params.put("stream", stream);
        params.put("force", force);
        return get("/index/api/close_stream", params, new TypeReference<>() {});
    }

    private <T> T get(String path, Map<String, Object> queryParams, TypeReference<ZlmResponse<T>> responseType) {
        String body = restClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(path);
                    queryParams.forEach((key, value) -> {
                        if (value != null) {
                            uriBuilder.queryParam(key, value);
                        }
                    });
                    return uriBuilder.build();
                })
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
        try {
            ZlmResponse<T> response = objectMapper.readValue(body, responseType);
            if (response.getCode() != 0) {
                throw new ZlmClientException("ZLMediaKit request failed: " + response.getMsg());
            }
            return response.getData();
        } catch (ZlmClientException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ZlmClientException("Failed to parse ZLMediaKit response", ex);
        }
    }

    private Map<String, Object> orderedParams() {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("secret", secret);
        return params;
    }
}