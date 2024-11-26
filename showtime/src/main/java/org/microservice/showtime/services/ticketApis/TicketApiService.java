package org.microservice.showtime.services.ticketApis;

import org.microservice.showtime.models.tickets.TicketRes;
import reactor.core.publisher.Flux;

public interface TicketApiService {
    
    Flux<TicketRes> findTicketsByOrderId(Long orderId);
}
