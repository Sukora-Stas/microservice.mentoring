package com.epam.microservice.service.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ErrorParserHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return createResponse(
                new RuntimeException(ex.getBindingResult().getFieldError().getDefaultMessage(), ex),
                status);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ParseMetadataException.class)
    public ResponseEntity<Object> handleEntityNotFound(ParseMetadataException e) {
        return createResponse(e, HttpStatus.NOT_FOUND);
    }


    private ResponseEntity<Object> createResponse(Exception ex, HttpStatus status) {
        ErrorDto error = new ErrorDto();
        error.setStatus(status);
        error.setCode(status.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), status);
    }
}
