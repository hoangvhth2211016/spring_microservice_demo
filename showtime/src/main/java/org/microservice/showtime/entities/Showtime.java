package org.microservice.showtime.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "showtimes")
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "movie_id", nullable = false)
    private Long movieId;
    
    @Column(name = "movie_title", nullable = false)
    private String movieTitle;
    
    @Column(name = "screen_id", nullable = false)
    private Long screenId;
    
    @Column(name = "screen_number", nullable = false)
    private Integer screenNumber;
    
    @Column(name = "from_time", nullable = false)
    private LocalDateTime fromTime;
    
    @Column(name = "to_time", nullable = false)
    private LocalDateTime toTime;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShowtimeSeat> showtimeSeats = new LinkedHashSet<>();
    
}