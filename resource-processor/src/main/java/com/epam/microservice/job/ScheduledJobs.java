package com.epam.microservice.job;

import com.epam.microservice.client.ResourceServiceClient;
import com.epam.microservice.client.SongServiceClient;
import com.epam.microservice.service.dto.ResourceStatusDTO;
import com.epam.microservice.service.ResourceMetadataProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledJobs {

    private ResourceServiceClient resourceServiceClient;
    private SongServiceClient songServiceClient;
    private ResourceMetadataProcessorService resourceMetadataProcessorService;

    @Autowired
    public ScheduledJobs(ResourceServiceClient resourceServiceClient,
                         SongServiceClient songServiceClient,
                         ResourceMetadataProcessorService resourceMetadataProcessorService) {
        this.resourceServiceClient = resourceServiceClient;
        this.songServiceClient = songServiceClient;
        this.resourceMetadataProcessorService = resourceMetadataProcessorService;
    }

    @Scheduled(initialDelay = 5, fixedRate = 60, timeUnit = TimeUnit.SECONDS)
    public void extractAndSaveMetadata() throws IOException {
        var resourceIds = resourceServiceClient.getUnprocessedResourceIds();

        for (Long resourceId : resourceIds) {
            if (!songServiceClient.songMetadataExists(resourceId)) {
                var binaryData = resourceServiceClient.getResourceBinaryData(resourceId);
                var metadata = resourceMetadataProcessorService.extractMetadata(resourceId, binaryData);

                songServiceClient.persistMetadata(metadata);
            }
            resourceServiceClient.updateResourceStatus(resourceId, new ResourceStatusDTO("COMPLETE"));
        }
    }

}
