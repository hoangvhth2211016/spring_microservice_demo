package org.microservice.movie.models.movies;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieApiRes {
    
    private Long id;
    
    private String title;
    
    private Integer runtime;
    
}
