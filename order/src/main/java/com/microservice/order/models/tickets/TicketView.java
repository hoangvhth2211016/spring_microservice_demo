package com.microservice.order.models.tickets;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketView {
    
    private Long showtimeSeatId;
    
    private String seatNumber;
    
    private BigDecimal price;
    
}
