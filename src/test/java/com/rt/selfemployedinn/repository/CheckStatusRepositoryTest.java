package com.rt.selfemployedinn.repository;

import com.rt.selfemployedinn.repository.data.RequestStatusSimple;
import com.rt.selfemployedinn.repository.data.error.ResponseException;
import com.rt.selfemployedinn.repository.data.ResponseStatus;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CheckStatusRepositoryTest {

    private final CheckStatusRepository _repository = new CheckStatusRepository("https://statusnpd.nalog.ru/api/v1/tracker/taxpayer_status");

    @Test
    void checkStatusSelfEmployed_NoSelf() throws ResponseException {
        ResponseStatus response = _repository.CheckStatusSelfEmployed(new RequestStatusSimple("7707049388", new Date()));

        assertFalse(response.isSelfEmployed());
        assertTrue(response.getMessage().contains("не является"));
    }

    @Test
    void checkStatusSelfEmployed_BadData() {
       try{
           ResponseStatus response = _repository.CheckStatusSelfEmployed(new RequestStatusSimple("7707049388", new Date(1111111)));

           fail();
       } catch (ResponseException re){
           assertEquals("validation.failed", re.getError().getCode());
           assertTrue(re.getError().getMessage().contains("деятельности начал действовать"));
       } catch (Exception ex){
           fail();
       }
    }
}