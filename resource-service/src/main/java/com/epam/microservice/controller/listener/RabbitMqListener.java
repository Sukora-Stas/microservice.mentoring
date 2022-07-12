package com.epam.microservice.controller.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class RabbitMqListener {

    @RabbitListener
    public void processQueue(String message) {}
}
