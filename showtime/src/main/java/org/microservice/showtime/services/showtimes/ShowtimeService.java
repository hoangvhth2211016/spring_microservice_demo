package org.microservice.showtime.services.showtimes;

import org.microservice.showtime.entities.Showtime;
import org.microservice.showtime.entities.ShowtimeSeat;
import org.microservice.showtime.models.pages.PageRes;
import org.microservice.showtime.models.showtimes.ShowtimeCreateReq;
import org.microservice.showtime.models.showtimes.ShowtimeDetailRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ShowtimeService {
    Page<Showtime> getAll(Pageable pageable, LocalDate on, Long movieId);
    
    Showtime create(ShowtimeCreateReq req);
    
    Showtime getById(Long id);
    
}
