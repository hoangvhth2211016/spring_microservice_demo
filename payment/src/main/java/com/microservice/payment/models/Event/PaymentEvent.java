package com.microservice.payment.models.Event;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentEvent {
    
    private Long orderId;
    
    private Long userId;
    
    private String paymentStatus;
    
    private LocalDateTime timeline = LocalDateTime.now();
    
}