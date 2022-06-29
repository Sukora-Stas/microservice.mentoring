package com.epam.microservice.service.dto;

public class ResourceStatusDTO {
    private String status;

    public ResourceStatusDTO() {
    }

    public ResourceStatusDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
