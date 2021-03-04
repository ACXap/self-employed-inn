package com.rt.selfemployedinn.services;

import com.rt.selfemployedinn.services.data.CheckedInn;
import com.rt.selfemployedinn.services.data.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckStatusApiService {

    private final CheckStatusService service;


    public String addTask(List<String> collectionInn) {
        return null;
    }

    public List<CheckedInn> getResultTask(String taskId) {
        return null;
    }

    public Status getStatusTask(String taskId) {
        return null;
    }
}