package org.microservice.movie.services.movies;

import org.microservice.movie.entities.Movie;
import org.microservice.movie.models.movies.MovieCreateReq;
import org.microservice.movie.models.movies.MovieStatus;
import org.microservice.movie.models.movies.MovieSchedule;
import org.microservice.movie.models.movies.MovieUpdateReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface MovieService {
    
    Page<Movie> getAll(Pageable pageable, MovieStatus status, MovieSchedule schedule);
    
    Movie getById(Long id);
    
    Movie create(MovieCreateReq req);
    
    
    Movie updateInternalInformation(Long id, MovieUpdateReq req);
    
    Movie getByIdAvailable(Long id);
    
    Movie getByIdAvailableFrom(Long id, LocalDateTime fromTime);
}
