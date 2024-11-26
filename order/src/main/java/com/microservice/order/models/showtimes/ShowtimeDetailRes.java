package com.microservice.order.models.showtimes;


import com.microservice.order.models.showtimeSeats.ShowtimeSeatRes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class ShowtimeDetailRes {
    
    private Long id;
    
    private Long movieId;
    
    private String movieTitle;
    
    private Long screenId;
    
    private Integer screenNumber;
    
    private LocalDateTime fromTime;
    
    private LocalDateTime toTime;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private Set<ShowtimeSeatRes> seats = new HashSet<>();
    
}
