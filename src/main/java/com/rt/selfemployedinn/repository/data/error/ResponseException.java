package com.rt.selfemployedinn.repository.data.error;

import lombok.Getter;

@Getter
public class ResponseException extends Exception{
    public ResponseException(ResponseError errorStatus, Throwable cause) {
        super(cause);
        error = errorStatus;
    }

    public ResponseException(ResponseError errorStatus, String message, Throwable cause) {
        super(message, cause);
        error = errorStatus;
    }

    private final ResponseError error;
}