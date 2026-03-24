package io.streamlinker.zlm.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddFfmpegSourceRequest {
    @JsonProperty("src_url")
    private String srcUrl;

    @JsonProperty("dst_url")
    private String dstUrl;

    @JsonProperty("timeout_ms")
    private Integer timeoutMs;

    @JsonProperty("enable_hls")
    private Boolean enableHls;

    @JsonProperty("enable_mp4")
    private Boolean enableMp4;

    @JsonProperty("ffmpeg_cmd_key")
    private String ffmpegCmdKey;
}
