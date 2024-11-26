package com.microservice.order.producers;

import com.microservice.order.models.events.OrderCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedEventHandler {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    private static final String EXCHANGE_NAME = "user-reservation-exchange";
    
    private static final String ROUTING_KEY_SUCCESS = "user.reservation.extend";
    
    private static final String ROUTING_KEY_FAILURE = "user.reservation.clear";
    
    public void handleUserReservationOrderCreated(OrderCreatedEvent event) {
        
        String routingKey = event.isSuccessfullyCreated() ? ROUTING_KEY_SUCCESS : ROUTING_KEY_FAILURE;
    
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey , event.getUserId());
    
    }
    
}
