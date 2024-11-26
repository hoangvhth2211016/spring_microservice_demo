package org.microservice.showtime.services.ticketApis;

import org.microservice.showtime.models.tickets.TicketRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class TicketApiServiceImpl implements TicketApiService{
    
    @Autowired
    private WebClient webClient;
    
    @Override
    public Flux<TicketRes> findTicketsByOrderId(Long orderId) {
        return webClient
                .get()
                .uri("orders/public/"+orderId+"/tickets")
                .retrieve()
                .bodyToFlux(TicketRes.class);
    }
    
}
