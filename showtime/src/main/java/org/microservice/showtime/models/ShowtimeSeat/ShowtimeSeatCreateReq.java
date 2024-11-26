package org.microservice.showtime.models.ShowtimeSeat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowtimeSeatCreateReq {
    
    @NotNull
    private Long seatId;
    
    @NotNull
    private String seatNumber;
    
    @NotNull
    private String seatType;
    
    @NotNull
    private String status;
    
}
