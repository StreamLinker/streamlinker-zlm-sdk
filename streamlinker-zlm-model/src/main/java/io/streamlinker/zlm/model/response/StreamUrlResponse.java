package io.streamlinker.zlm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreamUrlResponse {
    @JsonProperty("rtmp_url")
    private String rtmpUrl;

    @JsonProperty("rtsp_url")
    private String rtspUrl;

    @JsonProperty("flv_url")
    private String flvUrl;

    @JsonProperty("ws_flv_url")
    private String wsFlvUrl;

    @JsonProperty("hls_url")
    private String hlsUrl;

    @JsonProperty("webrtc_url")
    private String webrtcUrl;
}
