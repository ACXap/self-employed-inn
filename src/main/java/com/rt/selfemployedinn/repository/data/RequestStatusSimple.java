package com.rt.selfemployedinn.repository.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@NoArgsConstructor
public class RequestStatusSimple implements IStatusRequest {
    @JsonProperty("inn")
    public String Inn;
    @JsonProperty("requestDate")
    public String RequestDate;

    public RequestStatusSimple(String inn, Date dateRequest){
        Inn = inn;
        RequestDate = new SimpleDateFormat("yyyy-MM-dd").format(dateRequest);
    }
}