package org.microservice.showtime.services.movieApis;

import org.microservice.showtime.models.movies.MovieRes;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MovieApiService {
    
    Mono<MovieRes> findAvailableById(Long id, LocalDateTime fromTime);
    
}
