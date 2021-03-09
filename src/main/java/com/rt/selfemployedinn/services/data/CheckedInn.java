package com.rt.selfemployedinn.services.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
public class CheckedInn {
    @JsonProperty("inn")
    public final String Inn;
    @JsonProperty("checkdate")
    public final Date CheckDate;
    @JsonProperty("status")
    public final Boolean Status;
    @JsonProperty("error")
    public final String Error;
}