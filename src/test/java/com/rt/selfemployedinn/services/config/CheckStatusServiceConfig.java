package com.rt.selfemployedinn.services.config;

import com.rt.selfemployedinn.repository.CheckStatusRepository;
import com.rt.selfemployedinn.services.CheckStatusService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CheckStatusServiceConfig {
    @Bean
    public CheckStatusService checkStatusService(CheckStatusRepository repository){
        return new CheckStatusService(repository);
    }
}