package org.microservice.showtime.models.showtimes;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShowtimeRes {
    
    private Long id;
    
    private Long movieId;
    
    private String movieTitle;
    
    private Long screenId;
    
    private Integer screenNumber;
    
    private LocalDateTime fromTime;
    
    private LocalDateTime toTime;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
}
