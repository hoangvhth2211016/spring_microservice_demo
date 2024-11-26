package com.microservice.mail.models.events;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentEvent {
    
    private Long orderId;
    
    private Long userId;
    
    private String paymentStatus;
    
    private LocalDateTime timeline;
    
}
