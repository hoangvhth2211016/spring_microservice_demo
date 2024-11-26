package org.microservice.movie.models.movies;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieOmdbRes {
    
    @JsonProperty("Title")
    private String title;
    
    @JsonProperty("Genre")
    private String genre;
    
    @JsonProperty("Runtime")
    private String runtime;
    
    //@JsonProperty("Released")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMM yyyy")
    //private LocalDate released;
    
    @JsonProperty("Rated")
    private String rated;
    
    @JsonProperty("Director")
    private String director;
    
    @JsonProperty("Writer")
    private String writer;
    
    @JsonProperty("Actors")
    private String actor;
    
    @JsonProperty("Plot")
    private String description;
    
    @JsonProperty("Poster")
    private String poster;
    
    @JsonProperty("Awards")
    private String award;
    
    @JsonProperty("Metascore")
    private String metascore;
    
    @JsonProperty("imdbRating")
    private String imdbRating;
    
    @JsonProperty("imdbID")
    private String imdbId;
    
    @JsonProperty("Response")
    private Boolean response;
    
}
