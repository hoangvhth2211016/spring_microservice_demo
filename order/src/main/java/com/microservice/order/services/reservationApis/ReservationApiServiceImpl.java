package com.microservice.order.services.reservationApis;

import com.microservice.order.models.reservations.ReservationRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ReservationApiServiceImpl implements ReservationApiService {
    
    
    @Autowired
    private WebClient webClient;
    
    
    @Override
    public Flux<ReservationRes> getCartById(Long userId) {
        return webClient
                .get()
                .uri("reservations")
                .retrieve()
                .bodyToFlux(ReservationRes.class);
    }
}
