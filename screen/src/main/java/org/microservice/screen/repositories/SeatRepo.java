package org.microservice.screen.repositories;

import org.microservice.screen.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepo extends JpaRepository<Seat, Long> {
    
    List<Seat> findByScreen_Id(Long id);
    
    Optional<Seat> findByIdAndScreen_Id(Long seatId, Long screenId);
}