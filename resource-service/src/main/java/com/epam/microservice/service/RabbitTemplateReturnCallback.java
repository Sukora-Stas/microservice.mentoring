package com.epam.microservice.service;

import com.epam.microservice.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitTemplateReturnCallback implements RabbitTemplate.ReturnsCallback {

    private final ResourceRepository resourceRepository;

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        Long resourceId = bytesToLong(returned.getMessage().getBody());
        resourceRepository
                .findById(resourceId)
                .ifPresentOrElse(
                        resource -> {
                            resource.setSent(0);
                            resourceRepository.save(resource);
                        },
                        () ->
                                log.error(
                                        "The object whose Id({}) was tried to put in the queue was not found.",
                                        resourceId));
    }

    private long bytesToLong(byte[] bytes) {
        return new BigInteger(bytes).longValue();
    }
}
