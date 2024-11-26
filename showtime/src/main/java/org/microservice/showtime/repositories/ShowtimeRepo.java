package org.microservice.showtime.repositories;

import org.microservice.showtime.entities.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface ShowtimeRepo extends JpaRepository<Showtime, Long>, JpaSpecificationExecutor<Showtime> {
    
    boolean existsByScreenIdAndToTimeAfter(Long screenId, LocalDateTime fromTime);
    
    Optional<Showtime> findByShowtimeSeats_IdIn(Collection<Long> ids);
    
    
}