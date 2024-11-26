package com.microservice.user.models.users;

import com.microservice.user.entities.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRes {
    
    private User user;
    
    private String accessToken;
    
    private String refreshToken;
}
