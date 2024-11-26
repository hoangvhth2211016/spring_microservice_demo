package org.microservice.movie.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "trailer")
    private String trailer;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "genre", nullable = false)
    private String genre;
    
    @Column(name = "runtime", nullable = false)
    private Integer runtime;
    
    @Column(name = "released", nullable = false)
    private LocalDate released;
    
    @Column(name = "rated")
    private String rated;
    
    @Column(name = "director", nullable = false)
    private String director;
    
    @Column(name = "writer", nullable = false)
    private String writer;
    
    @Column(name = "actor", nullable = false)
    private String actor;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @Column(name = "poster")
    private String poster;
    
    @Column(name = "award")
    private String award;
    
    @Column(name = "metascore")
    private String metascore;
    
    @Column(name = "imdb_rating")
    private String imdbRating;
    
    @Column(name = "imdb_id", nullable = false, unique = true)
    private String imdbId;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    

    
}