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
public class MediaInfoResponse {
    private String app;
    private String stream;
    private String schema;

    @JsonProperty("origin_type")
    private Integer originType;

    @JsonProperty("reader_count")
    private Integer readerCount;
}
