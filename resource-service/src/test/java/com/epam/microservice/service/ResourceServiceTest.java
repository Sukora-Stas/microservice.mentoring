package com.epam.microservice.service;

import com.epam.microservice.model.Resource;
import com.epam.microservice.repository.ResourceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {
    @Mock
    private ResourceRepository resourceRepository;
    @Mock
    private AwsS3Service awsS3Service;
    @Mock
    private ResourceCreatedQueueProducerService resourceCreatedQueueProducerService;
    @InjectMocks
    private ResourceService resourceService;

    @Test
    void findById() throws IOException {
        // given
        Resource resource = mock(Resource.class);
        byte[] expectedFileContent = new byte[]{1, 2, 3, 4};

        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));
        when(awsS3Service.getFileBytesByKey(resource.getFileName())).thenReturn(expectedFileContent);
        // when
        var actualFileContent = resourceService.findById(1L);
        // then
        assertArrayEquals(expectedFileContent, actualFileContent.getData().readAllBytes());
    }

    @Test
    void save() {
        // given
        final String fileName = "file.mp3";
        final long resourceId = 1L;

        Resource resource = new Resource();
        resource.setFileName(fileName);
        MultipartFile multipartFile = mock(MultipartFile.class);
        Resource createdResource = new Resource();
        createdResource.setId(resourceId);
        createdResource.setFileName(fileName);

        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
        when(resourceRepository.save(resource)).thenReturn(createdResource);
        // when
        Long createdResourceId = resourceService.saveFile(multipartFile);
        // then
        assertEquals(resourceId, createdResourceId);
    }

    @Test
    void delete() {
        // given
        List<Long> inputIds = List.of(1L, 2L);
        Long[] expectedIds = new Long[]{1L};
        Resource resource = new Resource();
        resource.setId(1L);

        when(resourceRepository.deleteAllByIdIn(inputIds)).thenReturn(List.of(resource));
        // when
        Long[] actualIds = resourceService.delete(inputIds);
        // then
        assertArrayEquals(expectedIds, actualIds);
    }
}
