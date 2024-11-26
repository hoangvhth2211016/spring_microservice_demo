package com.microservice.payment.models.orders;

import com.microservice.payment.models.tickets.TicketRes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class OrderRes {
    
    private Long id;
    
    private Long userId;
    
    private Long showtimeId;
    
    private String status;
    
    private Set<TicketRes> tickets = new LinkedHashSet<>();

    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
}
