package org.microservice.showtime.models.tickets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
