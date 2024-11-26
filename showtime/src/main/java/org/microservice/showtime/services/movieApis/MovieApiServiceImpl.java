package org.microservice.showtime.services.movieApis;

import org.microservice.showtime.models.movies.MovieRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class MovieApiServiceImpl implements MovieApiService {
    
    @Autowired
    private WebClient webClient;
    
    
    @Override
    public Mono<MovieRes> findAvailableById(Long id, LocalDateTime fromTime) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path("/movies/public/{id}/available")
                        .queryParam("from", fromTime)
                        .build(id))
                .retrieve()
                .bodyToMono(MovieRes.class);
    }
}
