package com.epam.microservice.client;

import com.epam.microservice.model.SongMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SongServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${configuration.song-service-url}")
    private String songServiceUrl;

    public boolean songMetadataExists(Long resourceId) {
        var result = restTemplate.exchange(
                songServiceUrl + "?resourceId=" + resourceId,
                HttpMethod.GET,
                null,
                SongMetadata.class);
        return result.getBody() != null;
    }

    public void persistMetadata(SongMetadata songMetadata) {
        restTemplate.postForObject(
                songServiceUrl,
                songMetadata,
                String.class);
    }
}
