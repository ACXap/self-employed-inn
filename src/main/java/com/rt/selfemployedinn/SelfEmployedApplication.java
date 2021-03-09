package com.rt.selfemployedinn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SelfEmployedApplication {
	public static void main(String[] args) {
		SpringApplication.run(SelfEmployedApplication.class, args);
	}
}