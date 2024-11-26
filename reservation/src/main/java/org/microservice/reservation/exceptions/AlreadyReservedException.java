package org.microservice.reservation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyReservedException extends ResponseStatusException {
    
    public AlreadyReservedException() {
        super(HttpStatus.NOT_FOUND, "Seat already reserved");
    }

}
