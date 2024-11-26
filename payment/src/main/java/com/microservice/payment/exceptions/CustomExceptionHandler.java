package com.microservice.payment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {
    
    
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleCustomException(ResponseStatusException e){
        return ResponseEntity.status(e.getStatusCode()).body(e.getBody().getDetail());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e){
        List<String> errors = e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new HashMap<String, List<String>>(){{
                    put("errors", errors);
                }}
        );
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSecurityException(Exception e){
        if(e instanceof AccessDeniedException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized request");
        }else {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
    
}