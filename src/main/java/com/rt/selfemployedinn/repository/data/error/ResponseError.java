package com.rt.selfemployedinn.repository.data.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseError {
    public ResponseError(String code, String message) {
        _code = code;
        _message = message;
    }

    public ResponseError() {
    }

    @JsonProperty("code")
    private String _code;
    @JsonProperty("message")
    private String _message;

    public String getCode() {
        return _code;
    }

    public String getMessage() {
        return _message;
    }
}