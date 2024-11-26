package org.microservice.reservation.controllers;

import jakarta.validation.Valid;
import org.microservice.reservation.entities.Reservation;
import org.microservice.reservation.models.reservations.ReservationReq;
import org.microservice.reservation.models.users.UserInformation;
import org.microservice.reservation.services.reservations.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservations")
public class ReservationController {
    
    @Autowired
    private ReservationService reservationService;
    
    
    @GetMapping
    public List<Reservation> getByUserId(@AuthenticationPrincipal UserInformation userInfo){
        return reservationService.getByUserId(userInfo.getId());
    }
    
    @GetMapping("{showtimeSeatId}")
    public Reservation getById(@PathVariable Long showtimeSeatId){
        return reservationService.getById(showtimeSeatId);
    }
    
    @PostMapping
    public Reservation reserveSeat(@RequestBody @Valid ReservationReq req, @AuthenticationPrincipal UserInformation userInfo){
        return reservationService.create(userInfo.getId(), req);
    }
    
    @PatchMapping("{showtimeSeatId}")
    public String removeFromReservation(
            @AuthenticationPrincipal UserInformation userInfo,
            @PathVariable(value = "showtimeSeatId") Long showtimeSeatId
    ){
        reservationService.removeFromReservation(userInfo.getId(), showtimeSeatId);
        return "Seat removed";
    }
    
    @DeleteMapping
    public String clearReservation(@AuthenticationPrincipal UserInformation userInfo){
        reservationService.clearUserReservation(userInfo.getId());
        return "User reservation cleared";
    }
    
}

