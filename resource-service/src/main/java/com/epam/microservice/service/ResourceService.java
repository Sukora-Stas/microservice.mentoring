package com.epam.microservice.service;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.epam.microservice.model.Resource;
import com.epam.microservice.repository.ResourceRepository;
import com.epam.microservice.service.dto.ResourceDTO;
import com.epam.microservice.service.exceptions.EntityDuplicateException;
import com.epam.microservice.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final AwsS3Service awsS3Service;
    private final ResourceCreatedQueueProducerService resourceCreatedQueueProducerService;

    @Transactional
    public ResourceDTO findById(Long id) {
        var resource =
                resourceRepository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException(id));

        var fileName = resource.getFileName();

        if (!StringUtils.hasLength(fileName)) {
            throw new EntityNotFoundException("Can't find file with name" + fileName);
        }

        S3Object awsResource;
        try {
            awsResource = awsS3Service.downloadResource(fileName);
        } catch (AmazonS3Exception amazonS3Exception) {
            throw new EntityNotFoundException("Can't find file " + fileName + " on AWS S3");
        }

        return new ResourceDTO(fileName,
                awsResource.getObjectContent(),
                awsResource.getObjectMetadata().getContentLength());
    }

    @Transactional
    public Long saveFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        if (resourceRepository.existsByFileName(fileName)) {
            throw new EntityDuplicateException(fileName);
        }

        Resource resource = new Resource();
        resource.setFileName(fileName);

        awsS3Service.saveFile(file);
        resource = resourceRepository.save(resource);
        resourceCreatedQueueProducerService.produce(resource.getId());

        return resource.getId();
    }

    @Transactional
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    @Transactional
    public Long[] delete(List<Long> ids) {
        List<Resource> resources = resourceRepository.deleteAllByIdIn(ids);
        awsS3Service.deleteByFileKeys(resources.stream()
                .map(Resource::getFileName)
                .collect(Collectors.toList()));
        return resources.stream().map(Resource::getId).toArray(Long[]::new);
    }

    @Transactional
    public List<Long> getUnprocessedResourceIds() {
        var resources = resourceRepository.findAllBySent(0);

        if (!resources.isEmpty()) {
            var ids = new ArrayList<Long>();
            for (Resource resource : resources) {
                ids.add(resource.getId());
            }
            resourceRepository.saveAll(resources);
            return ids;
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional
    public void updateResource(Long id) {
        var resource = resourceRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException(id));

        resource.setSent(1);

        resourceRepository.save(resource);
    }
}
