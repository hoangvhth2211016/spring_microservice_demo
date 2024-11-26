package org.microservice.reservation.models.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreatedEvent {
    
    Long userId;
    
    boolean isSuccessfullyCreated;
    
}
