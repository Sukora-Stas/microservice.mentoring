package com.epam.microservice.service;

import com.epam.microservice.model.Resource;
import com.epam.microservice.repository.ResourceRepository;
import com.epam.microservice.service.exceptions.EntityDuplicateException;
import com.epam.microservice.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

@Service
public class ResourceService {

    private ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Transactional
    public byte[] findById(Long id) {
        Resource resource =
                resourceRepository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException(id));
        //TODO: change logic with aws

        // temporary
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(resource);
            oos.flush();
        } catch (Exception ex) {
            //nothing
        }
        // temporary

        return bos.toByteArray();
    }

    @Transactional
    public Long save(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (resourceRepository.existsByFileName(fileName)) {
            throw new EntityDuplicateException(fileName);
        }
        Resource resource = resourceRepository.save(
                new Resource(fileName,
                        Resource.ProcessingStatus.NONE));
        //TODO: change logic with aws
        /*String fileKey = awsS3Service.save(resource.getId(), file);
        resource.setFileKey(fileKey);*/
        resourceRepository.save(resource);
        return resource.getId();
    }

    @Transactional
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    @Transactional
    public Long[] delete(List<Long> ids) {
        List<Resource> resources = resourceRepository.deleteAllByIdIn(ids);
        //awsS3Service.deleteByFileKeys(resources.stream().map(Resource::getFileKey).toList());
        return resources.stream().map(Resource::getId).toArray(Long[]::new);
    }
}
