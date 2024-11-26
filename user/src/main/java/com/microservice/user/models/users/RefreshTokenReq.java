package com.microservice.user.models.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RefreshTokenReq {
    
    @NotBlank
    private String refreshToken;
    
}
