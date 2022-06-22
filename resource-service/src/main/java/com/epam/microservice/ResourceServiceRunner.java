package com.epam.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ResourceServiceRunner {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServiceRunner.class, args);
    }
}
