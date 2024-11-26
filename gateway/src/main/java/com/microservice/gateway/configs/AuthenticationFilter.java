package com.microservice.gateway.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.gateway.models.users.UserInformation;
import com.microservice.gateway.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class AuthenticationFilter implements GatewayFilter{
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    @Qualifier("AuthenticationReq")
    private WebClient webClient;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        
        String authHeader = request.getHeaders().getFirst("Authorization");

        if(authHeader == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized request");
        }
        String[] headerElements = authHeader.split(" ");

        if(headerElements.length == 2 && "Bearer".equals(headerElements[0])){
            String token = headerElements[1];
        //    return webClient
        //            .get()
        //            .uri(uriBuilder -> uriBuilder.path("/validation").queryParam("token", token).build())
        //            .retrieve()
        //            .bodyToMono(Boolean.class)
        //            .flatMap(isTokenValid -> {
        //                if(isTokenValid){
        //                    return chain.filter(exchange);
        //                }
        //                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"token invalid");
        //            })
        //            .onErrorResume(WebClientResponseException.class, e ->{
        //                return Mono.error(new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString(), e));
        //            })
        //            .onErrorResume(Exception.class,e ->{
        //                return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e));
        //            });
            return webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/validation")
                            .queryParam("token", token)
                            .build())
                    .retrieve()
                    .bodyToMono(UserInformation.class)
                    .flatMap(userInformation -> {
                        String userInfoJson = null;
                        try {
                            userInfoJson = objectMapper.writeValueAsString(userInformation);
                            ServerHttpRequest modifiedRequest = request
                                    .mutate()
                                    .header("X-User-Info", userInfoJson)
                                    .build();
                            return chain.filter(exchange.mutate().request(modifiedRequest).build());
                        } catch (JsonProcessingException e) {
                            return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to serialize object", e));
                        }
                    })
                    .onErrorResume(WebClientResponseException.class, e ->{
                        return Mono.error(new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString(), e));
                    });
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid token");
    }
}
