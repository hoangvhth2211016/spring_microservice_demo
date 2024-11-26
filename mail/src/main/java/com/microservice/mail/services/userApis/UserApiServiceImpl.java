package com.microservice.mail.services.userApis;

import com.microservice.mail.models.users.UserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserApiServiceImpl implements UserApiService {
    
    @Autowired
    private WebClient webClient;
    
    @Override
    public Mono<UserRes> getUserById(Long id) {
        return webClient.get()
                .uri("/users/public/{id}", id)
                .retrieve()
                .bodyToMono(UserRes.class);
    }
}
