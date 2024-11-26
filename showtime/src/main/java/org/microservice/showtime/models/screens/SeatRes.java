package org.microservice.showtime.models.screens;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
