package org.microservice.showtime.consumers;

import org.microservice.showtime.models.ShowtimeSeat.SeatStatus;
import org.microservice.showtime.models.events.OrderCompleteMailEvent;
import org.microservice.showtime.models.events.PaymentEvent;
import org.microservice.showtime.models.tickets.TicketRes;
import org.microservice.showtime.producers.ShowtimeSeatBookEventHandler;
import org.microservice.showtime.services.showtimeSeats.ShowtimeSeatService;
import org.microservice.showtime.services.ticketApis.TicketApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ShowtimeSeatEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(ShowtimeSeatEventListener.class);
    
    @Autowired
    private ShowtimeSeatService showtimeSeatService;
    
    @Autowired
    private TicketApiService ticketApiService;
    
    @Autowired
    private ShowtimeSeatBookEventHandler showtimeSeatBookEventHandler;
    
    @RabbitListener(queues = "seat-payment-succeed-queue")
    public void handleBookShowtimeSeatOnPaymentSuccess(PaymentEvent message) {
        logger.info("Book user showtime seat for successfully paid event for user -> {}", message.getUserId());
        
        List<TicketRes> tickets = Objects.requireNonNull(ticketApiService.findTicketsByOrderId(message.getOrderId())
                        .collectList()
                        .block());
        
        List<Long> showtimeSeatIds = tickets.stream()
                .map(TicketRes::getShowtimeSeatId)
                .toList();
        
        OrderCompleteMailEvent orderCompleteMailEvent = new OrderCompleteMailEvent();
        orderCompleteMailEvent.setUserId(message.getUserId());
        orderCompleteMailEvent.setTickets(tickets);
        
        showtimeSeatService.updateSeatStatus(showtimeSeatIds, SeatStatus.BOOKED);
        showtimeSeatBookEventHandler.clearUserReservationOnShowtimeSeatBooked(message.getUserId());
        showtimeSeatBookEventHandler.updateUserOrderOnShowtimeSeatBooked(message.getOrderId());
        showtimeSeatBookEventHandler.sendUserEmailOnShowtimeSeatBooked(orderCompleteMailEvent);
    }
    
}
