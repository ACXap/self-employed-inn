package com.rt.selfemployedinn.services.data;

import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
public class CheckedInn {
    public final String Inn;
    public final Date CheckDate;
    public final Boolean Status;
    public final String Error;
}