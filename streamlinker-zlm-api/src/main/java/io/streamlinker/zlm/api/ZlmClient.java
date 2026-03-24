package io.streamlinker.zlm.api;

import io.streamlinker.zlm.model.response.MediaInfoResponse;
import io.streamlinker.zlm.model.response.SimpleResultResponse;
import io.streamlinker.zlm.model.response.StreamKeyResponse;
import io.streamlinker.zlm.model.response.StreamUrlResponse;

public interface ZlmClient {

    StreamKeyResponse addFfmpegSource(String srcUrl,
                                      String dstUrl,
                                      Integer timeoutMs,
                                      Boolean enableHls,
                                      Boolean enableMp4,
                                      String ffmpegCmdKey);

    StreamKeyResponse addStreamProxy(String app,
                                     String stream,
                                     String url,
                                     Integer rtpType,
                                     Boolean enableMp4,
                                     Boolean autoClose);

    StreamKeyResponse addStreamPusherProxy(String app,
                                           String stream,
                                           String schema,
                                           String dstUrl,
                                           Integer rtpType);

    SimpleResultResponse delFfmpegSource(String key);

    SimpleResultResponse delStreamProxy(String key);

    SimpleResultResponse delStreamPusherProxy(String key);

    MediaInfoResponse getMediaInfo(String schema, String vhost, String app, String stream);

    StreamUrlResponse getStreamUrl(String schema, String vhost, String app, String stream);

    SimpleResultResponse closeStream(String schema, String vhost, String app, String stream, Boolean force);
}