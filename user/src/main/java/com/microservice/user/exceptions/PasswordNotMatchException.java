package com.microservice.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PasswordNotMatchException extends ResponseStatusException {
    
    public PasswordNotMatchException(){
        super(HttpStatus.UNAUTHORIZED, "Password not match");
    }
    
}