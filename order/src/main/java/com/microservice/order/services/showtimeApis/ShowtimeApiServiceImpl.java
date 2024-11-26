package com.microservice.order.services.showtimeApis;

import com.microservice.order.models.showtimeSeats.ShowtimeSeatRes;
import com.microservice.order.models.showtimes.ShowtimeDetailRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Set;

@Service
public class ShowtimeApiServiceImpl implements ShowtimeApiService{
    
    @Autowired
    private WebClient webClient;
    
    @Override
    public Flux<ShowtimeSeatRes> getSeatList(Collection<Long> showtimeSeatIds) {
        return webClient
                .get()
                .uri(uriBuilder ->{
                    uriBuilder.path("showtime_seats/public");
                    if (showtimeSeatIds != null && !showtimeSeatIds.isEmpty()) {
                        showtimeSeatIds.forEach(id -> uriBuilder.queryParam("ids", id));
                    }
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToFlux(ShowtimeSeatRes.class);
    }
}
