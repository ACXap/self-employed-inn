package com.rt.selfemployedinn.repository.data.error;

public class ResponseException extends Exception{
    public ResponseException(ResponseError errorStatus, Throwable cause) {
        super(cause);
        _error = errorStatus;
    }

    public ResponseException(ResponseError errorStatus, String message, Throwable cause) {
        super(message, cause);
        _error = errorStatus;
    }

    private final ResponseError _error;

    public ResponseError getError(){
        return _error;
    }
}