package com.microservice.mail.models.tickets;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TicketRes {
    
    private Long id;
    
    private Long showtimeSeatId;
    
    private String seatNumber;
    
    private BigDecimal price;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
}
