package com.rt.selfemployedinn.controllers;

import com.rt.selfemployedinn.services.CheckStatusApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/1.0/check-inn")
@Lazy
@RequiredArgsConstructor
public class ApiController {
    private final CheckStatusApiService service;

    @PostMapping
    public String checkInn(List<String> collectionInn){
        String taskId = service.addTask(collectionInn);

        return  taskId;
    }
}