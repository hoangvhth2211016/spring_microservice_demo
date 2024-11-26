package com.microservice.mail.models.events;

import com.microservice.mail.models.tickets.TicketRes;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderCompleteMailEvent {
    
    private Long userId;
    
    private List<TicketRes> tickets = new ArrayList<>();
    
}
