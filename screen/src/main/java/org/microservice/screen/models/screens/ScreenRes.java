package org.microservice.screen.models.screens;

import lombok.Getter;
import lombok.Setter;
import org.microservice.screen.models.seats.SeatRes;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ScreenRes {
    
    private Long id;
    
    private Integer number;

    private List<SeatRes> seats = new ArrayList<>();
    
}
