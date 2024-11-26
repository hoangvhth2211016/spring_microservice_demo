package org.microservice.screen.services.seats;

import org.microservice.screen.entities.Seat;
import org.microservice.screen.models.seats.SeatUpdateReq;

import java.util.List;

public interface SeatService {
    
    List<Seat> getAllByScreenId(Long screenId);

    Seat updateSeat(Long screenId, Long seatId, SeatUpdateReq req);
    
}
