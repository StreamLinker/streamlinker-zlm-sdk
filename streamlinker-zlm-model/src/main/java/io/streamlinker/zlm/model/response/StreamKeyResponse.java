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
public class StreamKeyResponse {
    private String key;

    @JsonProperty("local_port")
    private Integer localPort;
}
