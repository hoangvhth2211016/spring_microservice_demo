package com.microservice.payment.producers;

import com.microservice.payment.models.Event.PaymentEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventHandler {

    private static final String ORDER_EXCHANGE = "user-order-exchange";
    
    private static final String USER_RESERVATION_EXCHANGE = "user-reservation-exchange";
    
    private static final String MAIL_EXCHANGE = "mail-exchange";
    
    private static final String RESERVATION_CLEAR_ROUTING_KEY = "user.reservation.clear";
    
    private static final String ORDER_PAYMENT_EXPIRED_ROUTING_KEY = "payment.expired.order.cancel";
    
    private static final String SEAT_PAYMENT_SUCCESS_ROUTING_KEY = "payment.succeed.seat.book";
    
    private static final String ORDER_PAYMENT_ERROR_ROUTING_KEY = "payment.error.order.cancel";
    
    private static final String ORDER_MAIL_ERROR_ROUTING_KEY = "payment.error.order.error.mail";
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void updateSeatStatusOnPaymentSucceed(PaymentEvent message){
    rabbitTemplate.convertAndSend(ORDER_EXCHANGE, SEAT_PAYMENT_SUCCESS_ROUTING_KEY, message);
}
    
    public void updateOrderOnPaymentExpiredOrCancelled(Long orderId){
        rabbitTemplate.convertAndSend(ORDER_EXCHANGE, ORDER_PAYMENT_EXPIRED_ROUTING_KEY, orderId);
    }
    
    public void clearUserReservationOnPaymentExpiredOrCancelled(Long userId){
        rabbitTemplate.convertAndSend(USER_RESERVATION_EXCHANGE, RESERVATION_CLEAR_ROUTING_KEY, userId);
    }
    
    public void updateOrderOnPaymentError(Long orderId){
        rabbitTemplate.convertAndSend(ORDER_EXCHANGE, ORDER_PAYMENT_ERROR_ROUTING_KEY, orderId);
    }
    
    public void sendEmailOnPaymentError(PaymentEvent message){
        rabbitTemplate.convertAndSend(MAIL_EXCHANGE, ORDER_MAIL_ERROR_ROUTING_KEY, message);
    }
    
}
