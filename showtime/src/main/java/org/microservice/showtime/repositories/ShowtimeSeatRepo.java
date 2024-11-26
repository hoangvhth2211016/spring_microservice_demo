package org.microservice.showtime.repositories;

import org.microservice.showtime.entities.ShowtimeSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ShowtimeSeatRepo extends JpaRepository<ShowtimeSeat, Long> {
    
    List<ShowtimeSeat> findByIdIn(Collection<Long> ids);
    
    List<ShowtimeSeat> findByIdInAndStatusLike(Collection<Long> ids, String status);
}