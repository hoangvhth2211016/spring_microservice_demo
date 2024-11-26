package org.microservice.showtime.services.screenApis;

import org.microservice.showtime.models.screens.ScreenRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ScreenApiServiceImpl implements ScreenApiService{
    
    @Autowired
    private WebClient webClient;
    
    @Override
    public Mono<ScreenRes> findScreenById(Long id) {
        return webClient.get().uri("/screens/public/{id}", id)
                .retrieve()
                .bodyToMono(ScreenRes.class);
    }
}
