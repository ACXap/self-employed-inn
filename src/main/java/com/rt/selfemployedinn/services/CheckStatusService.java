package com.rt.selfemployedinn.services;

import com.rt.selfemployedinn.repository.CheckStatusRepository;
import com.rt.selfemployedinn.repository.data.RequestStatusSimple;
import com.rt.selfemployedinn.repository.data.ResponseStatus;
import com.rt.selfemployedinn.repository.data.error.ResponseException;
import com.rt.selfemployedinn.services.data.CheckedInn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CheckStatusService {
    private final CheckStatusRepository repository;

    public CheckedInn checkInn(String inn) {
        return checkInn(inn, new Date());
    }

    public CheckedInn checkInn(String inn, Date requestDate) {
        try {
            ResponseStatus responseStatus = repository.CheckStatusSelfEmployed(new RequestStatusSimple(inn, requestDate));

            return new CheckedInn(inn, new Date(), responseStatus.isSelfEmployed() , null);
        } catch (ResponseException e) {
            return new CheckedInn(inn, new Date(), null, e.getError().getMessage());
        }
    }
}