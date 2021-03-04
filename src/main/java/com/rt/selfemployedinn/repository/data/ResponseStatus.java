package com.rt.selfemployedinn.repository.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStatus {
    @JsonProperty("status")
    private boolean isSelfEmployed;
    @JsonProperty("message")
    private String message;
}