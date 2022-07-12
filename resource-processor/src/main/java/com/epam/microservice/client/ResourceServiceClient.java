package com.epam.microservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "resource-service", url = "${configuration.resource-service.url}")
public interface ResourceServiceClient {
    @Retryable(
            value = RuntimeException.class,
            maxAttemptsExpression = "${configuration.resource-service.request.retries-count}",
            backoff =
            @Backoff(
                    delayExpression = "${configuration.resource-service.request.base-retry-delay}",
                    maxDelayExpression = "${configuration.resource-service.request.max-retry-delay}",
                    multiplierExpression = "${configuration.resource-service.request.retry-delay-multiplier}"))
    @GetMapping(value = "/{resourceId}")
    byte[] getResourceBinaryData(@PathVariable Long resourceId);
}
