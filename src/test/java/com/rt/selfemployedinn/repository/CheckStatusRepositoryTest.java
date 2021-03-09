package com.rt.selfemployedinn.repository;

import com.rt.selfemployedinn.repository.config.RepositoryConfig;
import com.rt.selfemployedinn.repository.config.RepositoryStopConfig;
import com.rt.selfemployedinn.repository.data.RequestStatusSimple;
import com.rt.selfemployedinn.repository.data.error.ResponseException;
import com.rt.selfemployedinn.repository.data.ResponseStatus;
import com.rt.selfemployedinn.services.config.CheckStatusApiServiceConfig;
import com.rt.selfemployedinn.services.config.ServiceTestStopConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryConfig.class, RepositoryStopConfig.class})
@ActiveProfiles("test")
class CheckStatusRepositoryTest {

    @Autowired
    private CheckStatusRepository repository;

    @Test
    void checkStatusSelfEmployed_NoSelf() throws ResponseException {
        ResponseStatus response = repository.CheckStatusSelfEmployed(new RequestStatusSimple("7707049388", new Date()));

        assertFalse(response.isSelfEmployed());
        assertTrue(response.getMessage().contains("не является"));
    }

    @Test
    void checkStatusSelfEmployed_BadData() {
       try{
           ResponseStatus response = repository.CheckStatusSelfEmployed(new RequestStatusSimple("7707049388", new Date(1111111)));

           fail();
       } catch (ResponseException re){
           assertEquals("validation.failed", re.getError().getCode());
           assertTrue(re.getError().getMessage().contains("деятельности начал действовать"));
       } catch (Exception ex){
           fail();
       }
    }
}