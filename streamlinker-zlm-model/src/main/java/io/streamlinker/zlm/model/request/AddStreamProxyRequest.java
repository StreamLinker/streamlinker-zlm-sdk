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
public class AddStreamProxyRequest {
    private String app;
    private String stream;
    private String url;

    @JsonProperty("rtp_type")
    private Integer rtpType;

    @JsonProperty("enable_mp4")
    private Boolean enableMp4;

    @JsonProperty("auto_close")
    private Boolean autoClose;
}
