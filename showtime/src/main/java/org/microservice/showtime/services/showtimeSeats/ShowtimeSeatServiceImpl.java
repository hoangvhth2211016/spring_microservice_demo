package org.microservice.showtime.services.showtimeSeats;

import org.microservice.showtime.entities.ShowtimeSeat;
import org.microservice.showtime.exceptions.NotFoundException;
import org.microservice.showtime.models.ShowtimeSeat.SeatStatus;
import org.microservice.showtime.models.ShowtimeSeat.ShowtimeSeatCreateReq;
import org.microservice.showtime.models.ShowtimeSeat.ShowtimeSeatUpdateReq;
import org.microservice.showtime.repositories.ShowtimeSeatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ShowtimeSeatServiceImpl implements ShowtimeSeatService{
    
    @Autowired
    private ShowtimeSeatRepo showtimeSeatRepo;
    
    @Override
    public List<ShowtimeSeat> getSeatInList(Set<Long> showtimeSeatIds) {
        return showtimeSeatRepo.findByIdInAndStatusLike(showtimeSeatIds, SeatStatus.AVAILABLE.name());
    }
    
    @Override
    public ShowtimeSeat getById(Long id) {
        return showtimeSeatRepo.findById(id).orElseThrow(() -> new NotFoundException("Showtime seat not found"));
    }
    
    @Override
    public ShowtimeSeat create(ShowtimeSeatCreateReq req) {
        ShowtimeSeat showtimeSeat = new ShowtimeSeat();
        showtimeSeat.setSeatId(req.getSeatId());
        showtimeSeat.setSeatNumber(req.getSeatNumber());
        showtimeSeat.setSeatType(req.getSeatType());
        showtimeSeat.setStatus(req.getStatus());
        return showtimeSeatRepo.save(showtimeSeat);
    }
    
    @Override
    public ShowtimeSeat update(Long id, ShowtimeSeatUpdateReq req) {
        ShowtimeSeat seat = getById(id);
        seat.setStatus(req.getStatus().name());
        return showtimeSeatRepo.save(seat);
    }
    
    @Override
    public void updateSeatStatus(List<Long> showtimeSeatIds, SeatStatus seatStatus) {
        List<ShowtimeSeat> showtimeSeats = showtimeSeatRepo.findByIdInAndStatusLike(showtimeSeatIds, SeatStatus.AVAILABLE.name());
        showtimeSeats.forEach(showtimeSeat -> {
            showtimeSeat.setStatus(seatStatus.name());
        });
        showtimeSeatRepo.saveAll(showtimeSeats);
    }
}
