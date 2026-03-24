package io.streamlinker.zlm.api;

public class ZlmClientException extends RuntimeException {
    public ZlmClientException(String message) {
        super(message);
    }

    public ZlmClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
