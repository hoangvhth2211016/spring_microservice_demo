package org.microservice.movie.models.movies;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovieCreateReq {
    
    @NotBlank
    private String title;
    
    @NotNull
    private LocalDate released;
    
    private String trailer;
    
    private MovieStatus status;
    
}
