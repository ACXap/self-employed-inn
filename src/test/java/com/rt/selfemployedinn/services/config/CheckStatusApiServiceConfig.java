package com.rt.selfemployedinn.services.config;

import com.rt.selfemployedinn.services.CheckStatusApiService;
import com.rt.selfemployedinn.services.CheckStatusService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CheckStatusApiServiceConfig {
    @Bean
    public CheckStatusApiService checkStatusServiceApi(CheckStatusService service){
        return new CheckStatusApiService(service);
    }
}