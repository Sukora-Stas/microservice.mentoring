package com.epam.microservice.client;

import com.epam.microservice.model.SongMetadata;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "song-service", url = "${configuration.song-service.url}")
public interface SongServiceClient {
    @Retryable(
            value = RuntimeException.class,
            maxAttemptsExpression = "${configuration.song-service.request.retries-count}",
            backoff =
            @Backoff(
                    delayExpression = "${configuration.song-service.request.base-retry-delay}",
                    maxDelayExpression = "${configuration.song-service.request.max-retry-delay}",
                    multiplierExpression = "${configuration.song-service.request.retry-delay-multiplier}"))
    @PostMapping
    Map<String, Long> saveSong(SongMetadata songMetadata);
}