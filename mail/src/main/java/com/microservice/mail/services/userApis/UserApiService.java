package com.microservice.mail.services.userApis;

import com.microservice.mail.models.users.UserRes;
import reactor.core.publisher.Mono;

public interface UserApiService {
    
    Mono<UserRes> getUserById(Long id);
    
}
