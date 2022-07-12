package com.epam.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableFeignClients
public class ResourceProcessorServiceRunner {

    public static void main(String[] args) {
        SpringApplication.run(ResourceProcessorServiceRunner.class, args);
    }
}
