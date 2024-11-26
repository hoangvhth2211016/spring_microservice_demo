package com.microservice.order.models.orders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateMessage {
    
    private Long orderId;
    
    private PaymentStatus paymentStatus;
    
}
