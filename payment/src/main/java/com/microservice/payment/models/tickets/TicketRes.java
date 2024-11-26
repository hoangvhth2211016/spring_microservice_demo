package com.microservice.payment.models.tickets;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TicketRes {
    
    private Long id;
    
    private Long showtimeSeatId;
    
    private String seatNumber;
    
    private BigDecimal price;
    
}
