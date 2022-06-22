package com.epam.microservice.controller.exceptions;

import org.springframework.http.HttpStatus;

public class ErrorDto {
    private HttpStatus status;
    private int code;
    private String message;

    public ErrorDto(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ErrorDto() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
