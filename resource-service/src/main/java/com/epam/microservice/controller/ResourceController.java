package com.epam.microservice.controller;

import com.epam.microservice.model.Resource;
import com.epam.microservice.service.ResourceService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping(value = "/{id}", produces = "application/octet-stream")
    public ResponseEntity<InputStreamResource> getResource(@PathVariable Long id) throws IOException {
        var resource = resourceService.findById(id);
        var stream = new InputStreamResource(resource.getData());
        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment; filename=" + resource.getFileName())
                .body(stream);
    }

    @GetMapping
    public List<Resource> getAll() {
        return resourceService.findAll();
    }

    @GetMapping("/unprocessed")
    public List<Resource> getUnprocessedResourceAudioBinaryData() {
        //TODO: not required
        return resourceService.getUnprocessedResourceIds();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Long>> create(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(Map.of("id", resourceService.saveFile(file)));
    }

    @DeleteMapping(params = "ids")
    public ResponseEntity<Map<String, Long[]>> delete(@RequestParam @Length(max = 200) String ids) {
        var deletedIds =
                resourceService.delete(
                        Arrays.stream(ids.split(","))
                                .map(Long::parseLong)
                                .collect(Collectors.toList()));
        return ResponseEntity.ok(Map.of("ids", deletedIds));
    }
}
