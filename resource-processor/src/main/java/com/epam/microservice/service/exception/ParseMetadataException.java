package com.epam.microservice.service.exception;

public class ParseMetadataException extends RuntimeException {

    public ParseMetadataException(String message) {
        super(message);
    }

    public ParseMetadataException(Long resourceId, Exception ex) {
        super("Error parse resource with id = " + resourceId, ex);
    }
}
