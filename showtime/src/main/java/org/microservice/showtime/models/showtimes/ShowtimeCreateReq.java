package org.microservice.showtime.models.showtimes;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShowtimeCreateReq {
    
    @NotNull
    private Long movieId;
    
    @NotNull
    private Long screenId;
    
    @NotNull
    private LocalDateTime fromTime;
    
}
