package org.microservice.showtime.services.showtimeSeats;

import org.microservice.showtime.entities.ShowtimeSeat;
import org.microservice.showtime.models.ShowtimeSeat.SeatStatus;
import org.microservice.showtime.models.ShowtimeSeat.ShowtimeSeatCreateReq;
import org.microservice.showtime.models.ShowtimeSeat.ShowtimeSeatRes;
import org.microservice.showtime.models.ShowtimeSeat.ShowtimeSeatUpdateReq;

import java.util.List;
import java.util.Set;

public interface ShowtimeSeatService {
    
    List<ShowtimeSeat> getSeatInList(Set<Long> showtimeSeatIds);
    
    ShowtimeSeat getById(Long id);
    
    ShowtimeSeat create(ShowtimeSeatCreateReq req);
    
    ShowtimeSeat update(Long id, ShowtimeSeatUpdateReq req);
    
    void updateSeatStatus(List<Long> showtimeSeatIds, SeatStatus seatStatus);
}
