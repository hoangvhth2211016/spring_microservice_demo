package com.microservice.order.services.showtimeApis;

import com.microservice.order.models.showtimeSeats.ShowtimeSeatRes;
import com.microservice.order.models.showtimes.ShowtimeDetailRes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Set;

public interface ShowtimeApiService {
    
    Flux<ShowtimeSeatRes> getSeatList(Collection<Long> showtimeSeatIds);
    
}
