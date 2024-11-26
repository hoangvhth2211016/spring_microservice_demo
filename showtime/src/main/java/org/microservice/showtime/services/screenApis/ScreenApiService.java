package org.microservice.showtime.services.screenApis;

import org.microservice.showtime.models.screens.ScreenRes;
import reactor.core.publisher.Mono;

public interface ScreenApiService {
    
    Mono<ScreenRes> findScreenById(Long id);
    
}
