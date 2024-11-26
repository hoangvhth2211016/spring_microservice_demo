package org.microservice.screen.models.seats;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatRes {
    
    private Long id;
    
    private Integer columnNr;
    
    private Character rowNr;
    
    private String type;
    
}
