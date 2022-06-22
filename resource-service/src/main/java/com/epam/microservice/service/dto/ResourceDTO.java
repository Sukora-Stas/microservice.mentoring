package com.epam.microservice.service.dto;

import java.io.InputStream;

public class ResourceDTO {

    private String fileName;
    private InputStream data;
    private Long size;

    public ResourceDTO(String fileName, InputStream data, Long size) {
        this.fileName = fileName;
        this.data = data;
        this.size = size;
    }

    public ResourceDTO() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getData() {
        return data;
    }

    public void setData(InputStream data) {
        this.data = data;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ResourceDTO{" +
                "fileName='" + fileName + '\'' +
                ", data=" + data +
                ", size=" + size +
                '}';
    }
}
