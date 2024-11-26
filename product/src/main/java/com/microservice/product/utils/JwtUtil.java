package com.microservice.product.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.microservice.product.models.users.UserInformation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


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
    
    public List<String> extractRoles(String token){
        return decodeToken(token).getClaim("roles").asList(String.class);
    }
    
    //public UserInformation extractUserInformation(String token){
    //    Map<String, Claim> claims = decodeToken(token).getClaims();
    //    return UserInformation.builder()
    //            .id(claims.get("id").asLong())
    //            .username(claims.get("username").asString())
    //            .roles(claims.get("roles").asList(String.class))
    //            .build();
    //}
    
}

