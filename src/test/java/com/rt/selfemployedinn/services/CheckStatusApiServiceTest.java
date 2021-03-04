package com.rt.selfemployedinn.services;

import com.rt.selfemployedinn.services.config.CheckStatusApiServiceConfig;
import com.rt.selfemployedinn.services.config.ServiceTestStopConfig;
import com.rt.selfemployedinn.services.data.CheckedInn;
import com.rt.selfemployedinn.services.data.Status;
import com.rt.selfemployedinn.services.data.StatusType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.AnswersWithDelay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CheckStatusApiServiceConfig.class, ServiceTestStopConfig.class})
@ActiveProfiles("test")
class CheckStatusApiServiceTest {

    @MockBean
    private CheckStatusService serv;
    @Autowired
    private CheckStatusApiService api;

    @Test
    void getStatusTask_NoTask() {
        try{
            Status statusTask = api.getStatusTask(UUID.randomUUID().toString());
            fail();
        } catch (Exception e) {
           assertEquals("Id not found", e.getMessage());
        }
    }

    @Test
    void getResultTask_NoTask() {
        try{
            List<CheckedInn> resultTask = api.getResultTask(UUID.randomUUID().toString());
            fail();
        } catch (Exception e) {
            assertEquals("Id not found", e.getMessage());
        }
    }

    @Test
    void getResultTask_CompletedTask() throws Exception {
        Mockito.doReturn(new CheckedInn("111", new Date(), true, "")).when(serv).checkInn("111");

        String taskId = api.addTask(Collections.singletonList("111"));

        while (true){
            Thread.sleep(100);
            Status statusTask = api.getStatusTask(taskId);

            if(statusTask.getStatus().equals(StatusType.COMPLETED)) break;
        }

        List<CheckedInn> resultTask = api.getResultTask(taskId);

        assertEquals(true, resultTask.get(0).Status);
        assertEquals("111", resultTask.get(0).Inn);
        assertEquals("", resultTask.get(0).Error);
    }

    @Test
    void getResultTask_NoCompletedTask() throws Exception {
        Mockito.doAnswer( new AnswersWithDelay(5000, invocationOnMock -> new CheckedInn("111", new Date(), true, ""))).when(serv).checkInn("111");

        String taskId = api.addTask(Collections.singletonList("111"));

        while (true){
            Status statusTask = api.getStatusTask(taskId);

            if(statusTask.getStatus().equals(StatusType.START)) {
                try{
                    api.getResultTask(taskId);
                    fail();
                } catch (Exception e){
                    assertEquals("Task not completed", e.getMessage());
                    break;
                }
            } else if(statusTask.getStatus().equals(StatusType.COMPLETED)){
                fail();
            }
        }
    }

    @Test
    void addTask_maxQueue() {
        Mockito.doAnswer( new AnswersWithDelay(10000, invocationOnMock -> new CheckedInn("111", new Date(), true, ""))).when(serv).checkInn("111");

        try{
            for(int i = 0;i<12;i++){
                String taskId = api.addTask(Collections.singletonList("111"));
            }
            fail();
        } catch (Exception ex){
            assertTrue(ex.getMessage().contains("rejected from java.util.concurrent.ThreadPoolExecutor"));
        }

    }
}