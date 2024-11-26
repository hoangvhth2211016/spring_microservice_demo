package org.microservice.showtime.producers;

import org.microservice.showtime.models.events.OrderCompleteMailEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShowtimeSeatBookEventHandler {
    
    private static final String USER_RESERVATION_EXCHANGE = "user-reservation-exchange";
    
    private static final String ORDER_EXCHANGE = "user-order-exchange";
    
    private static final String MAIL_EXCHANGE = "mail-exchange";
    
    private static final String RESERVATION_CLEAR_ROUTING_KEY = "user.reservation.clear";
    
    private static final String ORDER_COMPLETE_ROUTING_KEY = "payment.succeed.order.complete";
    
    private static final String ORDER_MAIL_SUCCESS_ROUTING_KEY = "payment.succeed.order.complete.mail";
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void clearUserReservationOnShowtimeSeatBooked(Long userId){
        rabbitTemplate.convertAndSend(USER_RESERVATION_EXCHANGE, RESERVATION_CLEAR_ROUTING_KEY , userId);
    }
    
    public void updateUserOrderOnShowtimeSeatBooked(Long orderId){
        rabbitTemplate.convertAndSend(ORDER_EXCHANGE, ORDER_COMPLETE_ROUTING_KEY, orderId);
    }
    
    public void sendUserEmailOnShowtimeSeatBooked(OrderCompleteMailEvent message){
        rabbitTemplate.convertAndSend(MAIL_EXCHANGE, ORDER_MAIL_SUCCESS_ROUTING_KEY, message);
    }
    
}
