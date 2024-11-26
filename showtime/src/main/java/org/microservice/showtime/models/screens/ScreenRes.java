package org.microservice.showtime.models.screens;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ScreenRes {
    
    private Long id;
    
    private Integer number;

    private List<SeatRes> seats = new ArrayList<>();
    
}
