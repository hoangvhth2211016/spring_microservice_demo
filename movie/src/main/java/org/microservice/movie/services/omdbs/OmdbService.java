package org.microservice.movie.services.omdbs;

import org.microservice.movie.models.movies.MovieOmdbRes;
import reactor.core.publisher.Mono;

public interface OmdbService {
    
    Mono<MovieOmdbRes> getMovieFromOmdb(String title);
    
}
