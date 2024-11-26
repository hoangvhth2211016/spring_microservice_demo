package org.microservice.reservation.models.reservations;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReservationReq {
    
    @NotNull
    private Long showtimeSeatId;
    
    @NotNull
    private Long showtimeId;
    
}
