package com.rt.selfemployedinn.services.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class Status {
    private final StatusType status;
    private final Date dateStatus;
    private final String message;

    public static Status Start(String message) {
        return new Status(StatusType.START, new Date(), message);
    }

    public static Status Stop(String message) {
        return new Status(StatusType.STOP, new Date(), message);
    }

    public static Status Error(String message) {
        return new Status(StatusType.ERROR, new Date(), message);
    }
}