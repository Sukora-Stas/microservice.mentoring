package com.epam.microservice.service.exceptions;

public class EntityDuplicateException extends RuntimeException {

    public EntityDuplicateException() {
    }

    public EntityDuplicateException(Long id) {
        super("Song with resource id = " + id + " already exists.");
    }

    public EntityDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityDuplicateException(Throwable cause) {
        super(cause);
    }
}
