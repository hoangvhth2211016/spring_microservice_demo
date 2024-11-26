package org.microservice.showtime.models.events;

import lombok.Getter;
import lombok.Setter;
import org.microservice.showtime.models.tickets.TicketRes;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderCompleteMailEvent {
    
    private Long userId;
    
    private List<TicketRes> tickets;
    
}
