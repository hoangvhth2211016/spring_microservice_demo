package com.microservice.order.models.reservations;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRes {
    
    private Long userId;
    
    private Long showtimeId;
    
    private Long showtimeSeatId;
    
}
