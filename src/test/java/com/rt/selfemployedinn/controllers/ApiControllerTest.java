package com.rt.selfemployedinn.controllers;

import com.rt.selfemployedinn.services.CheckStatusApiService;
import com.rt.selfemployedinn.services.data.CheckedInn;
import com.rt.selfemployedinn.services.data.Status;
import com.rt.selfemployedinn.services.data.StatusType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Collections;
import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ApiController.class)
class ApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CheckStatusApiService service;

    @Test
    void apiCheckInn() throws Exception {
        Mockito.when(service.addTask(ArgumentMatchers.anyList())).thenReturn("taskId");

        mockMvc.perform(get("/api/1.0/check-inn").param("inn", "123321123"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("taskId"));
    }

    @Test
    void apiGetStatusTask_TaskCompleted() throws Exception {
        Status status = new Status(StatusType.COMPLETED, new Date(0), "all completed");

        Mockito.when(service.getStatusTask(ArgumentMatchers.any())).thenReturn(status);

        mockMvc.perform(get("/api/1.0/check-inn/status").param("id", "taskId"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(content().json("{'status':'COMPLETED'}"))
                .andExpect(content().json("{'message':'all completed'}"));
    }

    @Test
    void apiGetStatusTask_TaskNotCompleted() throws Exception {
        Status status = new Status(StatusType.START, new Date(0), "not completed");

        Mockito.when(service.getStatusTask(ArgumentMatchers.any())).thenReturn(status);

        mockMvc.perform(get("/api/1.0/check-inn/status").param("id", "taskId"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(content().json("{'status':'START'}"))
                .andExpect(content().json("{'message':'not completed'}"));
    }

    @Test
    void apiGetStatusTask_TaskNotFound() throws Exception {
        Mockito.when(service.getStatusTask(ArgumentMatchers.any())).thenThrow(new Exception("Id not found"));

        mockMvc.perform(get("/api/1.0/check-inn/status").param("id", "taskId"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(containsString("Id not found")));
    }

    @Test
    void apiGetStatusTask_TaskIdNotCorrect() throws Exception {
        Mockito.when(service.getStatusTask(ArgumentMatchers.any())).thenThrow(new Exception("Id not correct"));

        mockMvc.perform(get("/api/1.0/check-inn/status").param("id", "taskId"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(containsString("Id not correct")));
    }

    @Test
    void apiGetResultTask_TaskCompleted() throws Exception {
        CheckedInn inn = new CheckedInn("123", new Date(0), true, "");

        Mockito.when(service.getResultTask(ArgumentMatchers.any())).thenReturn(Collections.singletonList(inn));

        mockMvc.perform(get("/api/1.0/check-inn/result").param("id", "taskId"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(content().json("[{'inn':'123'}]"))
                .andExpect(content().json("[{'status':true}]"))
                .andExpect(content().json("[{'error':''}]"));
    }

    @Test
    void apiGetResultTask_TaskNotCompleted() throws Exception {
        Mockito.when(service.getResultTask(ArgumentMatchers.any())).thenThrow(new Exception("Task not completed"));
        mockMvc.perform(get("/api/1.0/check-inn/result").param("id", "taskId"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(containsString("Task not completed")));
    }
}