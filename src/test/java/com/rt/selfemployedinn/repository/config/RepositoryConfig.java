package com.rt.selfemployedinn.repository.config;

import com.rt.selfemployedinn.repository.CheckStatusRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RepositoryConfig {

    @Value("${proxy.server}")
    private String server;

    @Value("${proxy.port}")
    private int port;

    @Bean
    public CheckStatusRepository checkStatusRepository(){
        return new CheckStatusRepository(server, port );
    }
}