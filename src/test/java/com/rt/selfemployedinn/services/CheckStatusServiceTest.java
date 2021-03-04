package com.rt.selfemployedinn.services;

import com.rt.selfemployedinn.repository.CheckStatusRepository;
import com.rt.selfemployedinn.repository.data.RequestStatusSimple;
import com.rt.selfemployedinn.repository.data.ResponseStatus;
import com.rt.selfemployedinn.repository.data.error.ResponseError;
import com.rt.selfemployedinn.repository.data.error.ResponseException;
import com.rt.selfemployedinn.services.config.CheckStatusServiceConfig;
import com.rt.selfemployedinn.services.config.ServiceTestStopConfig;
import com.rt.selfemployedinn.services.data.CheckedInn;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CheckStatusServiceConfig.class, ServiceTestStopConfig.class})
@ActiveProfiles("test")
class CheckStatusServiceTest {

    @MockBean
    private CheckStatusRepository repository;
    @Autowired
    private CheckStatusService service;

    @Test
    void checkInn_NoSelf() throws ResponseException {
        String inn = "7707049388";
        Mockito.doReturn(new ResponseStatus())
                .when(repository).CheckStatusSelfEmployed(ArgumentMatchers.any(RequestStatusSimple.class));

        CheckedInn checkedInn = service.checkInn(inn);

        assertEquals(false, checkedInn.Status);
        assertEquals(inn, checkedInn.Inn);
    }

    @Test
    void checkInn_YesSelf() throws ResponseException {
        String inn = "7707049388";
        Mockito.doReturn(new ResponseStatus(true, "Yes"))
                .when(repository).CheckStatusSelfEmployed(ArgumentMatchers.any(RequestStatusSimple.class));

        CheckedInn checkedInn = service.checkInn(inn);

        assertEquals(true, checkedInn.Status);
        assertEquals(inn, checkedInn.Inn);
    }

    @Test
    void checkInn_Error() throws ResponseException {
        String inn = "7707049388";
        Mockito.doThrow(new ResponseException(new ResponseError("code", "TestError"), new Exception()))
                .when(repository).CheckStatusSelfEmployed(ArgumentMatchers.any(RequestStatusSimple.class));

        CheckedInn checkedInn = service.checkInn(inn);

        assertNull(checkedInn.Status);
        assertEquals(inn, checkedInn.Inn);
        assertEquals("TestError", checkedInn.Error);
    }
}