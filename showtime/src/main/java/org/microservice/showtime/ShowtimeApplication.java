package org.microservice.showtime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ShowtimeApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ShowtimeApplication.class, args);
    }
    
}
