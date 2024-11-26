package com.microservice.user.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.microservice.user.entities.Role;
import com.microservice.user.entities.User;
import com.microservice.user.models.users.UserInformation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

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
    
    public String createAccessToken(User user){
        return JWT.create()
                .withIssuer(issuer)
                .withClaim("username", user.getUsername())
                .withClaim("id", user.getId())
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60))
                .sign(algorithm);
    }
    
    public String createRefreshToken(User user){
        return JWT.create()
                .withIssuer(issuer)
                .withClaim("username", user.getUsername())
                .withClaim("id", user.getId())
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24))
                .sign(algorithm);
    }
    
    public DecodedJWT decodeToken(String token){
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
    
    public String extractUsername(String token){
        return decodeToken(token).getClaim("username").asString();
    }
    
    public UserInformation extractUserInformation(String token){
        Map<String, Claim> claims = decodeToken(token).getClaims();
        return UserInformation.builder()
                .id(claims.get("id").asLong())
                .username(claims.get("username").asString())
                .roles(claims.get("roles").asList(String.class))
                .build();
    }
    
}

