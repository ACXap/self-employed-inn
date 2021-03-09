package com.rt.selfemployedinn.repository.config;

import com.rt.selfemployedinn.repository.CheckStatusRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RepositoryConfig {
    @Bean
    public CheckStatusRepository checkStatusRepository(){
        return new CheckStatusRepository();
    }
}