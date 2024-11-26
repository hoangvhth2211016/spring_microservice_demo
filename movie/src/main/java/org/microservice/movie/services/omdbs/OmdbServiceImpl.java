package org.microservice.movie.services.omdbs;

import org.microservice.movie.models.movies.MovieOmdbRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OmdbServiceImpl implements OmdbService {
    
    @Value("${omdb.api-key}")
    private String omdbApiKey;
    
    @Autowired
    @Qualifier("omdbApiService")
    private WebClient webClient;
    
    @Override
    public Mono<MovieOmdbRes> getMovieFromOmdb(String title) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", omdbApiKey)
                        .queryParam("t", title)
                        .build())
                .retrieve()
                .bodyToMono(MovieOmdbRes.class);
    }
}
