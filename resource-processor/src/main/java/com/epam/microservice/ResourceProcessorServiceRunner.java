package com.epam.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ResourceProcessorServiceRunner {

    public static void main(String[] args) {
        SpringApplication.run(ResourceProcessorServiceRunner.class, args);
    }
}
