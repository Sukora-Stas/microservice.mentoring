package com.epam.microservice.client;

import com.epam.microservice.service.dto.ResourceStatusDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ResourceServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${configuration.resource-service-url}")
    private String resourceUrlService;

    public InputStream getResourceBinaryData(Long id) throws IOException {
        var data = restTemplate.exchange(
                resourceUrlService + id,
                HttpMethod.GET,
                null, Resource.class);
        return data.getBody().getInputStream();
    }

    public List<Long> getUnprocessedResourceIds() {
        var exchange =
                restTemplate.exchange(
                        resourceUrlService + "/unprocessed",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Long>>() {
                        });

        return exchange.getBody();
    }

    public void updateResourceStatus(Long id, ResourceStatusDTO status) {
        restTemplate.put(
                resourceUrlService + id,
                status,
                ResourceStatusDTO.class
        );
    }
}
