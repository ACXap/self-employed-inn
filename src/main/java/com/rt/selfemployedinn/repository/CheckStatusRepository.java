package com.rt.selfemployedinn.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rt.selfemployedinn.repository.data.IStatusRequest;
import com.rt.selfemployedinn.repository.data.error.ResponseError;
import com.rt.selfemployedinn.repository.data.error.ResponseException;
import com.rt.selfemployedinn.repository.data.ResponseStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Repository
public class CheckStatusRepository {

    public CheckStatusRepository(@Value("${proxy.server:#{null}}") String server, @Value("${proxy.port:#{0}}") int port) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        if (server != null && !server.isEmpty() && port != 0) {
            requestFactory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(server, port)));
        }

        restTemplate = new RestTemplate(requestFactory);
    }

    //region PrivateField
    private final RestTemplate restTemplate;

    @Value("${url.service.check.status}")
    private String url;

    //endregion PrivateField

    //region PublicMethod

    public ResponseStatus CheckStatusSelfEmployed(IStatusRequest request) throws ResponseException {
        try {
            ResponseEntity<ResponseStatus> statusResponseResponseEntity = restTemplate.postForEntity(url, request, ResponseStatus.class);
            return statusResponseResponseEntity.getBody();
        } catch (HttpStatusCodeException exception) {
            throw createErrorStatus(exception);
        } catch (Exception ex) {
            throw new ResponseException(new ResponseError("no service error", ex.getMessage()), ex);
        }
    }

    //endregion PublicMethod

    //region PrivateMethod

    private ResponseException createErrorStatus(HttpStatusCodeException exception) {
        try {
            ResponseError result = new ObjectMapper().readValue(exception.getResponseBodyAsString(), ResponseError.class);
            return new ResponseException(result, exception.getMessage(), exception);
        } catch (Exception e) {
            return new ResponseException(new ResponseError("no service error", e.getMessage()), exception.getMessage(), exception);
        }
    }

    //endregion PrivateMethod
}