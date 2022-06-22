package com.epam.microservice.controller.exceptions;

import com.epam.microservice.service.exceptions.EntityDuplicateException;
import com.epam.microservice.service.exceptions.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorResponseEntityHandler extends ResponseEntityExceptionHandler {

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

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, ConstraintViolationException.class})
    public ResponseEntity<Object> handlePath(Exception e) {
        return createResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException e) {
        return createResponse(e, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityDuplicateException.class)
    public ResponseEntity<Object> handleEntityDuplicate(EntityDuplicateException e) {
        return createResponse(e, HttpStatus.CONFLICT);
    }

    private ResponseEntity<Object> createResponse(Exception ex, HttpStatus status) {
        ErrorDto error = new ErrorDto();
        error.setStatus(status);
        error.setCode(status.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), status);
    }
}
