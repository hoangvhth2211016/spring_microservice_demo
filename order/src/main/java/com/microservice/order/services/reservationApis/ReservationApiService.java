package com.microservice.order.services.reservationApis;

import com.microservice.order.models.reservations.ReservationRes;
import reactor.core.publisher.Flux;

public interface ReservationApiService {

    Flux<ReservationRes> getCartById(Long userId);

}
