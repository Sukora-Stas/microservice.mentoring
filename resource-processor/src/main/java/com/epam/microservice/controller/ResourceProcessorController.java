package com.epam.microservice.controller;

import com.epam.microservice.client.ResourceServiceClient;
import com.epam.microservice.model.SongMetadata;
import com.epam.microservice.service.ResourceMetadataProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/processor")
public class ResourceProcessorController {

    @Autowired
    private ResourceMetadataProcessorService resourceMetadataProcessorService;

    @GetMapping(value = "/{id}")
    public SongMetadata process(@PathVariable Long id) throws IOException {
        return resourceMetadataProcessorService.extractAndSaveMetadata(id);
    }
}
