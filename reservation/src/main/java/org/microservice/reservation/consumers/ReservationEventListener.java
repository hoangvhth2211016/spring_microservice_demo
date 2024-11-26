package org.microservice.reservation.consumers;


import org.microservice.reservation.models.events.OrderCreatedEvent;
import org.microservice.reservation.services.reservations.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(ReservationEventListener.class);
    
    @Autowired
    private ReservationService reservationService;
    
    @RabbitListener(queues = "user-reservation-extend-queue")
    public void handleUserReservationOnOrderCreatedSuccess(Long userId) {
        logger.info("Processing extend reservation for order of user -> {}", userId);
        reservationService.extendUserReservation(userId);
    }
    
    @RabbitListener(queues = "user-reservation-clear-queue")
    public void handleUserReservationOnOrderCreatedFailure(Long userId) {
        logger.info("Processing clear reservation for order of user -> {}", userId);
        reservationService.clearUserReservation(userId);
    }
    
}
