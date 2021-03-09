package com.rt.selfemployedinn.controllers;

import com.rt.selfemployedinn.services.CheckStatusApiService;
import com.rt.selfemployedinn.services.data.CheckedInn;
import com.rt.selfemployedinn.services.data.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/1.0/check-inn")
@Lazy
@RequiredArgsConstructor
public class ApiController {
    private final CheckStatusApiService service;

    @PostMapping
    public String apiCheckCollectionInn(List<String> collectionInn) {
        try {
            String taskId = service.addTask(collectionInn);
            return taskId;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping
    public String apiCheckSingleInn(@RequestParam("inn") String inn) {
        try {
            String taskId = service.addTask(Collections.singletonList(inn));
            return taskId;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping(path = "/status")
    public Status apiGetStatusTask(@RequestParam("id") String id) {
        try {
            return service.getStatusTask(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping(path = "/result")
    public List<CheckedInn> apiGetResultTask(@RequestParam("id") String id) {
        try {
            return service.getResultTask(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}