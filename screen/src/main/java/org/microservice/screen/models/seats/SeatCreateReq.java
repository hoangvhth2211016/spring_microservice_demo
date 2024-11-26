package org.microservice.screen.models.seats;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatCreateReq {
    
    @NotNull
    private Integer columnNr;
    
    @NotBlank
    private Character rowNr;
    
    @NotBlank
    private SeatType type;
    
}
