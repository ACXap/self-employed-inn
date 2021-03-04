package com.rt.selfemployedinn.repository.data.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError {
    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;
}