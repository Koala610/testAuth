package com.auth.test.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ValidationException;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        String errorMessage = "Validation failed: " + ex.getLocalizedMessage();

        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleMethodNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = "Method not valid exception: " + ex.getLocalizedMessage();

        return ResponseEntity.badRequest().body(errorMessage);
    }
}
