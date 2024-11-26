package org.microservice.screen.services.seats;

import org.microservice.screen.entities.Seat;
import org.microservice.screen.exceptions.NotFoundException;
import org.microservice.screen.models.seats.SeatUpdateReq;
import org.microservice.screen.repositories.SeatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    
    @Autowired
    private SeatRepo seatRepo;
    
    @Override
    public List<Seat> getAllByScreenId(Long screenId) {
        return seatRepo.findByScreen_Id(screenId);
    }
    
    @Override
    public Seat updateSeat(Long screenId, Long seatId, SeatUpdateReq req) {
        Seat seat = seatRepo.findByIdAndScreen_Id(seatId, screenId).orElseThrow(() -> new NotFoundException("Seat Not Found"));
        seat.setColumnNr(req.getColumnNr());
        seat.setRowNr(req.getRowNr());
        seat.setType(req.getType());
        return seatRepo.save(seat);
    }
}
