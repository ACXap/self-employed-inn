package com.rt.selfemployedinn.services;

import com.rt.selfemployedinn.repository.CheckStatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest()

class CheckStatusServiceTest {

    @MockBean
    private CheckStatusRepository repository;
    @Autowired
    private CheckStatusService service;

    @Test
    void checkInn_NoSelf() {
        


    }
}