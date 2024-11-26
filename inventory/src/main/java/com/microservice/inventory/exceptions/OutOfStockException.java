package com.microservice.inventory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OutOfStockException extends ResponseStatusException {
    
    public OutOfStockException() {
        super(HttpStatus.BAD_REQUEST, "Out Of Stock");
    }
    
    
}
