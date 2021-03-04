package com.rt.selfemployedinn.services;

import com.rt.selfemployedinn.repository.CheckStatusRepository;
import com.rt.selfemployedinn.services.data.CheckedInn;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CheckStatusService {

    public CheckStatusService(CheckStatusRepository repository) {
        _repository = repository;
    }

    private final CheckStatusRepository _repository;

    public CheckedInn checkInn(String inn) {
        return checkInn(inn, new Date());
    }

    public CheckedInn checkInn(String inn, Date requestDate) {
        return null;
    }
}