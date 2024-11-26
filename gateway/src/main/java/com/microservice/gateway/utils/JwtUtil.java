package com.microservice.gateway.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JwtUtil {
    
    
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    
    @Value("${security.jwt.token.issuer}")
    private String issuer;
    
    private Algorithm algorithm;
    
    @PostConstruct
    protected void init(){
        algorithm = Algorithm.HMAC256(secretKey);
    }
    
    public DecodedJWT decodeToken(String token){
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
    
    public String extractUsername(String token){
        return decodeToken(token).getClaim("username").asString();
    }
    
    public boolean isTokenValid(String token, String username){
        String currentUsername = extractUsername(token);
        return currentUsername.equals(username);
    }
    
}
