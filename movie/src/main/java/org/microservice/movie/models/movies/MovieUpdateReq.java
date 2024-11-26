package org.microservice.movie.models.movies;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovieUpdateReq {
    
    private String trailer;
    
    @NotBlank
    private LocalDate released;
    
    @NotBlank
    private MovieStatus status;
    
}
