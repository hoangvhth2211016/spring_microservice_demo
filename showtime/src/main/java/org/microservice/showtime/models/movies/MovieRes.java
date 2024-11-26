package org.microservice.showtime.models.movies;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieRes {
    
    private Long id;
    
    private String title;
    
    private Integer runtime;
    
}
