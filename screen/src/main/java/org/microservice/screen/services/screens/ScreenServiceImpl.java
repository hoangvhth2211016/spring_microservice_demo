package org.microservice.screen.services.screens;

import jakarta.transaction.Transactional;
import org.microservice.screen.entities.Screen;
import org.microservice.screen.entities.Seat;
import org.microservice.screen.exceptions.NotFoundException;
import org.microservice.screen.models.screens.ScreenCreateReq;
import org.microservice.screen.models.screens.ScreenUpdateReq;
import org.microservice.screen.models.seats.SeatType;
import org.microservice.screen.models.seats.SeatUpdateReq;
import org.microservice.screen.repositories.ScreenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class ScreenServiceImpl implements ScreenService {
    
    @Autowired
    private ScreenRepo screenRepo;
    
    @Override
    public Page<Screen> getAll(Pageable pageable) {
        return screenRepo.findAll(pageable);
    }
    
    @Override
    public Screen createScreenAndSeats(ScreenCreateReq req) {
        Screen screen = new Screen();
        screen.setNumber(req.getNumber());
        screen.setNumbersOfRow(req.getNumbersOfRow());
        screen.setNumbersOfColumn(req.getNumbersOfColumn());
        
        Set<Seat> seats = new HashSet<>();
        for (int row = 0; row < req.getNumbersOfRow(); row++) {
            char rowChar = (char) ('A' + row);
            for (int col = 1; col <= req.getNumbersOfColumn(); col++) {
                Seat seat = new Seat();
                seat.setScreen(screen);
                seat.setRowNr(rowChar);
                seat.setColumnNr(col);
                
                if (rowChar == 'A' + req.getNumbersOfRow() - 1 && col <= req.getNumberOfCouple()) {
                    seat.setType(SeatType.COUPLE.name());
                } else if (rowChar >= req.getVipFromRow() && rowChar <= req.getVipToRow() &&
                        col >= req.getVipFromColumn() && col <= req.getNumbersOfColumn() - req.getVipFromColumn() + 1) {
                    seat.setType(SeatType.VIP.name());
                } else {
                    seat.setType(SeatType.NORMAL.name());
                }
                seat.setScreen(screen);
                seats.add(seat);
            }
        }
        screen.setSeats(seats);
        return screenRepo.save(screen);
    }
    
    @Override
    public Screen getById(Long id) {
        return screenRepo.findById(id).orElseThrow(() -> new NotFoundException("Screen Not Found"));
    }
    
    @Override
    public Screen updateScreen(Long id, ScreenUpdateReq req) {
        Screen screen = screenRepo.findById(id).orElseThrow(() -> new NotFoundException("Screen Not Found"));
        screen.setNumber(req.getNumber());
        screen.setNumbersOfRow(req.getNumbersOfRow());
        screen.setNumbersOfColumn(req.getNumbersOfColumn());
        if(!req.getSeatUpdateReqs().isEmpty()){
            for (SeatUpdateReq seatUpdateReq : req.getSeatUpdateReqs()) {
                Seat seat = screen.getSeats().stream().filter(s -> s.getId().equals(seatUpdateReq.getId())).findFirst().orElseThrow(() -> new NotFoundException("Seat Not Found"));
                seat.setRowNr(seatUpdateReq.getRowNr());
                seat.setColumnNr(seatUpdateReq.getColumnNr());
                seat.setType(seatUpdateReq.getType());
            }
        }
        return screenRepo.save(screen);
    }
}
