package com.rt.selfemployedinn.repository.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseStatus {
    public ResponseStatus() {
    }

    @JsonProperty("status")
    private boolean _status;
    @JsonProperty("message")
    private String _message;

    public boolean getStatus() {
        return _status;
    }

    public String getMessage() {
        return _message;
    }
}