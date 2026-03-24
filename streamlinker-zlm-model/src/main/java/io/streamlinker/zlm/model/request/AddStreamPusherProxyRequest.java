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
public class AddStreamPusherProxyRequest {
    private String app;
    private String stream;
    private String schema;

    @JsonProperty("dst_url")
    private String dstUrl;

    @JsonProperty("rtp_type")
    private Integer rtpType;
}
