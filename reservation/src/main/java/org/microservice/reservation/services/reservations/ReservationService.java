package org.microservice.reservation.services.reservations;


import org.microservice.reservation.entities.Reservation;
import org.microservice.reservation.models.reservations.ReservationReq;

import java.util.List;

public interface ReservationService {
    
    Reservation getById(Long showtimeSeatId);
    
    Reservation create(Long userId, ReservationReq req);
    
    void removeFromReservation(Long userId, Long showtimeSeatId);
    
    void clearUserReservation(Long userId);
    
    List<Reservation> getByUserId(Long userId);
    
    void extendUserReservation(Long userId);
    
}
