package com.epam.microservices;

import com.epam.microservice.service.ResourceMetadataProcessorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class ResourceProcessorServiceTest {
    @Autowired
    private ResourceMetadataProcessorService resourceProcessorService;

    @Test
    void retrieveResourceMetadata() {

    }
}
