package com.microservice.payment.configs;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqManager {
    
    
    private static final String USER_ORDER_EXCHANGE = "user-order-exchange";
    
    private static final String USER_RESERVATION_EXCHANGE = "user-reservation-exchange";
    
    private static final String MAIL_EXCHANGE = "mail-exchange";
    
    private static final String DEAD_LETTER_EXCHANGE = "dead-letter-exchange";
    
    private static final String ORDER_DEAD_LETTER_QUEUE = "order-dead-letter-queue";
    
    private static final String ORDER_PAYMENT_SUCCEED_QUEUE = "order-payment-succeed-queue";
    
    private static final String ORDER_PAYMENT_EXPIRED_QUEUE = "order-payment-expired-queue";
    
    private static final String ORDER_PAYMENT_ERROR_QUEUE = "order-payment-error-queue";
    
    private static final String ORDER_MAIL_COMPLETE_QUEUE = "order-mail-complete-queue";
    
    private static final String SEAT_PAYMENT_SUCCEED_QUEUE = "seat-payment-succeed-queue";
    
    private static final String USER_RESERVATION_CLEAR_QUEUE = "user-reservation-clear-queue";
    
    private static final String USER_RESERVATION_EXTEND_QUEUE = "user-reservation-extend-queue";
    
    private static final String USER_REGISTRATION_MAIL_QUEUE = "user-registration-mail-queue";
    
    private static final String MAIL_DEAD_LETTER_QUEUE = "mail-dead-letter-queue";
    
    private static final String ORDER_DEAD_LETTER_KEY = "order.dead-letter";
    
    private static final String MAIL_DEAD_LETTER_KEY = "mail.dead-letter";
    
    private static final String ORDER_COMPLETE_ROUTING_KEY = "payment.succeed.order.complete";
    
    private static final String ORDER_CANCEL_ROUTING_KEY = "payment.expired.order.cancel";
    
    private static final String ORDER_PAYMENT_ERROR_ROUTING_KEY = "payment.error.order.cancel";
    
    private static final String ORDER_MAIL_SUCCESS_ROUTING_KEY = "payment.succeed.order.complete.mail";
    
    private static final String SEAT_PAYMENT_SUCCESS_ROUTING_KEY = "payment.succeed.seat.book";
    
    private static final String RESERVATION_CLEAR_ROUTING_KEY = "user.reservation.clear";
    
    private static final String RESERVATION_EXTEND_ROUTING_KEY = "user.reservation.extend";
    
    private static final String USER_REGISTRATION_MAIL_KEY = "user.registration.mail";
    
    private static final String ORDER_MAIL_ERROR_QUEUE = "order-mail-error-queue";
    
    private static final String ORDER_MAIL_ERROR_ROUTING_KEY = "payment.error.order.error.mail";
    
    @Bean
    public Queue orderMailErrorQueue() {
        return QueueBuilder
                .durable(ORDER_MAIL_ERROR_QUEUE)
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(ORDER_DEAD_LETTER_KEY)
                .build();
    }
    
    @Bean
    public Binding orderMailErrorBinding(Queue orderMailErrorQueue, TopicExchange mailExchange) {
        return BindingBuilder
                .bind(orderMailErrorQueue)
                .to(mailExchange)
                .with(ORDER_MAIL_ERROR_ROUTING_KEY);
    }
    
    @Bean
    public TopicExchange userOrderExchange() {
        return new TopicExchange(USER_ORDER_EXCHANGE);
    }
    
    @Bean
    public TopicExchange userReservationExchange() {
        return new TopicExchange(USER_RESERVATION_EXCHANGE);
    }
    
    @Bean
    public TopicExchange mailExchange() {
        return new TopicExchange(MAIL_EXCHANGE);
    }
    
    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DEAD_LETTER_EXCHANGE);
    }
    
    @Bean
    public Queue orderPaymentSuceedQueue() {
        return QueueBuilder
                .durable(ORDER_PAYMENT_SUCCEED_QUEUE)
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(ORDER_DEAD_LETTER_KEY)
                .build();
    }
    
    @Bean
    public Queue orderPaymentExpiredQueue() {
        return QueueBuilder
                .durable(ORDER_PAYMENT_EXPIRED_QUEUE)
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(ORDER_DEAD_LETTER_KEY)
                .build();
    }
    
    @Bean
    public Queue orderPaymentErrorQueue() {
        return QueueBuilder
                .durable(ORDER_PAYMENT_ERROR_QUEUE)
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(ORDER_DEAD_LETTER_KEY)
                .build();
    }
    
    @Bean
    public Queue seatPaymentSuceedQueue() {
        return QueueBuilder
                .durable(SEAT_PAYMENT_SUCCEED_QUEUE)
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(ORDER_DEAD_LETTER_KEY)
                .build();
    }
    
    @Bean
    public Queue userReservationClearQueue() {
        return QueueBuilder
                .durable(USER_RESERVATION_CLEAR_QUEUE)
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(ORDER_DEAD_LETTER_KEY)
                .build();
    }
    
    @Bean
    public Queue orderDeadLetterQueue() {
        return QueueBuilder
                .durable(ORDER_DEAD_LETTER_QUEUE)
                .build();
    }
    
    @Bean
    public Queue userReservationExtendQueue() {
        return QueueBuilder
                .durable(USER_RESERVATION_EXTEND_QUEUE)
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(ORDER_DEAD_LETTER_KEY)
                .build();
    }
    
    @Bean
    public Queue userRegistrationMailQueue() {
        return QueueBuilder
                .durable(USER_REGISTRATION_MAIL_QUEUE)
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(MAIL_DEAD_LETTER_KEY)
                .build();
    }
    
    @Bean
    public Queue mailDeadLetterQueue() {
        return QueueBuilder
                .durable(MAIL_DEAD_LETTER_QUEUE)
                .build();
    }
    
    @Bean
    public Queue orderMailCompleteQueue() {
        return QueueBuilder
                .durable(ORDER_MAIL_COMPLETE_QUEUE)
                .build();
    }
    
    @Bean
    public Binding mailDeadLetterBinding(Queue mailDeadLetterQueue, TopicExchange deadLetterExchange) {
        return BindingBuilder
                .bind(mailDeadLetterQueue)
                .to(deadLetterExchange)
                .with(MAIL_DEAD_LETTER_KEY);
    }
    
    @Bean
    public Binding orderDeadLetterBinding(Queue orderDeadLetterQueue, TopicExchange deadLetterExchange) {
        return BindingBuilder
                .bind(orderDeadLetterQueue)
                .to(deadLetterExchange)
                .with(ORDER_DEAD_LETTER_KEY);
    }
    
    @Bean
    public Binding paymentSuccessBinding(Queue orderPaymentSuceedQueue, TopicExchange userOrderExchange) {
        return BindingBuilder
                .bind(orderPaymentSuceedQueue)
                .to(userOrderExchange)
                .with(ORDER_COMPLETE_ROUTING_KEY);
    }
    
    @Bean
    public Binding paymentExpiredOrCancelBinding(Queue orderPaymentExpiredQueue, TopicExchange userOrderExchange) {
        return BindingBuilder
                .bind(orderPaymentExpiredQueue)
                .to(userOrderExchange)
                .with(ORDER_CANCEL_ROUTING_KEY);
    }
    
    @Bean
    public Binding paymentErrorBinding(Queue orderPaymentErrorQueue, TopicExchange userOrderExchange) {
        return BindingBuilder
                .bind(orderPaymentErrorQueue)
                .to(userOrderExchange)
                .with(ORDER_PAYMENT_ERROR_ROUTING_KEY);
    }
    
    @Bean
    public Binding seatPaymentSuccessBinding(Queue seatPaymentSuceedQueue, TopicExchange userOrderExchange) {
        return BindingBuilder
                .bind(seatPaymentSuceedQueue)
                .to(userOrderExchange)
                .with(SEAT_PAYMENT_SUCCESS_ROUTING_KEY);
    }
    
    @Bean
    public Binding reservationClearBinding(Queue userReservationClearQueue, TopicExchange userReservationExchange) {
        return BindingBuilder
                .bind(userReservationClearQueue)
                .to(userReservationExchange)
                .with(RESERVATION_CLEAR_ROUTING_KEY);
    }
    
    @Bean
    public Binding reservationExtendBinding(Queue userReservationExtendQueue, TopicExchange userReservationExchange) {
        return BindingBuilder
                .bind(userReservationExtendQueue)
                .to(userReservationExchange)
                .with(RESERVATION_EXTEND_ROUTING_KEY);
    }
    
    @Bean
    public Binding userRegistrationMailBinding(Queue userRegistrationMailQueue, TopicExchange mailExchange) {
        return BindingBuilder
                .bind(userRegistrationMailQueue)
                .to(mailExchange)
                .with(USER_REGISTRATION_MAIL_KEY);
    }
    
    @Bean
    public Binding orderCompleteMailBinding(Queue orderMailCompleteQueue, TopicExchange mailExchange) {
        return BindingBuilder
                .bind(orderMailCompleteQueue)
                .to(mailExchange)
                .with(ORDER_MAIL_SUCCESS_ROUTING_KEY);
    }
    
}
