package com.microservice.gateway.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RoutesConfig {
    
    @Autowired
    private AuthenticationFilter authenticationFilter;
    
    @Autowired
    private Environment env;
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("users-public",
                        r -> r.path("/users/public/**")
                                .uri(env.getProperty("USERS_URL")))
                .route("users",
                        r -> r.path("/users/**")
                                .filters(f -> f.filter(authenticationFilter))
                                .uri(env.getProperty("USERS_URL")))
                .route("auth",
                        r -> r.path("/auth/**")
                                .uri(env.getProperty("USERS_URL")))
                .route("reservations",
                        r -> r.path("/reservations/**")
                                .filters(f->f.filter(authenticationFilter))
                                .uri(env.getProperty("RESERVATIONS_URL")))
                .route("orders-public",
                        r -> r.path("/orders/public/**")
                                .uri(env.getProperty("ORDERS_URL")))
                .route("orders",
                        r -> r.path("/orders/**")
                                .filters(f -> f.filter(authenticationFilter))
                                .uri(env.getProperty("ORDERS_URL")))
                .route("payments-public",
                        r -> r.path("/payments/public/**")
                                .uri(env.getProperty("PAYMENTS_URL")))
                .route("payments",
                        r -> r.path("/payments/**")
                                .filters(f -> f.filter(authenticationFilter))
                                .uri(env.getProperty("PAYMENTS_URL")))
                .route("movies_public",
                        r -> r.path("/movies/public/**")
                                .uri(env.getProperty("MOVIES_URL")))
                .route("movies",
                        r -> r.path("/movies/**")
                                .filters(f -> f.filter(authenticationFilter))
                                .uri(env.getProperty("MOVIES_URL")))
                .route("screens_public",
                        r -> r.path("/screens/public/**")
                                .uri(env.getProperty("SCREENS_URL")))
                .route("screens",
                        r -> r.path("/screens/**")
                                .filters(f -> f.filter(authenticationFilter))
                                .uri(env.getProperty("SCREENS_URL")))
                .route("showtimes_public",
                        r -> r.path("/showtimes/public/**")
                                .uri(env.getProperty("SHOWTIMES_URL")))
                .route("showtimes",
                        r -> r.path("/showtimes/**")
                                .filters(f -> f.filter(authenticationFilter))
                                .uri(env.getProperty("SHOWTIMES_URL")))
                .route("showtime_seats_public",
                        r -> r.path("/showtime_seats/public/**")
                                .uri(env.getProperty("SHOWTIMES_URL")))
                .route("showtime_seats",
                        r -> r.path("/showtime_seats/**")
                                .filters(f -> f.filter(authenticationFilter))
                                .uri(env.getProperty("SHOWTIMES_URL")))
                .build();
    }
}
