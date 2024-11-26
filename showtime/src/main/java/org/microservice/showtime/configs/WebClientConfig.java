package org.microservice.showtime.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {
    
    @Value("${server.gateway}")
    private String baseUrl;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Bean
    public WebClient webClient(){
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
                })
                .build();
        return WebClient.builder()
                .baseUrl(baseUrl)
                .exchangeStrategies(strategies)
                .filter(addAuthenticationFilter())
                .filter(addErrorHandlingFilter())
                .build();
    }
    
    private ExchangeFilterFunction addAuthenticationFilter(){
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null){
                String token = authentication.getCredentials().toString();
                if(token != null && !token.isEmpty()){
                    ClientRequest newRequest = ClientRequest.from(clientRequest)
                            .headers(headers -> headers.setBearerAuth(token))
                            .build();
                    return Mono.just(newRequest);
                }
            }
            return Mono.just(clientRequest);
        });
    }
    
    private ExchangeFilterFunction addErrorHandlingFilter(){
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().isError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            return Mono.error(new ResponseStatusException(clientResponse.statusCode(), errorBody));
                        });
            }
            return Mono.just(clientResponse);
        });
    }
    
}
