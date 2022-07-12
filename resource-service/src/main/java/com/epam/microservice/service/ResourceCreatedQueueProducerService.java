package com.epam.microservice.service;

import com.epam.microservice.model.Resource;
import com.epam.microservice.repository.ResourceRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Recover;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResourceCreatedQueueProducerService {

    private final RabbitTemplate rabbitTemplate;
    private final ResourceRepository resourceRepository;
    private final RabbitTemplateReturnCallback returnCallback;

    @Value("${spring.rabbitmq.exchange-name}")
    private String resourceCreatedEventExchangeName;

    public void produce(Long resourceId) {
        rabbitTemplate.setExchange(resourceCreatedEventExchangeName);
        rabbitTemplate.setReturnsCallback(returnCallback);
        rabbitTemplate.convertAndSend(resourceId);
    }

    @Recover
    public void recover(RuntimeException e, Long resourceId) {
        resourceRepository
                .findById(resourceId)
                .ifPresentOrElse(
                        this::markResourceAsUnsent,
                        () ->
                                log.error(
                                        "The object whose Id({}) was tried to put in the queue was not found.",
                                        resourceId));
    }

    private void markResourceAsUnsent(Resource resource) {
        resource.setSent(0);
        resourceRepository.save(resource);
    }

    @Scheduled(fixedRateString = "${spring.rabbitmq.producer.task-retry-delay}")
    public void sendUnsentFiles() {
        try {
            List<Resource> resources = resourceRepository.findAllBySent(0);
            for (Resource resource : resources) {
                produce(resource.getId());
                resource.setSent(1);
                resourceRepository.save(resource);
            }
        } catch (RuntimeException e) {
            log.error("Error sending Ids to the queue", e);
        }
    }
}
