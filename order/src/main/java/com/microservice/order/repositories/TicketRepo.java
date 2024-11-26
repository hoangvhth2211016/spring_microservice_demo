package com.microservice.order.repositories;

import com.microservice.order.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket, Long> {
    
    
    List<Ticket> findByOrder_Id(Long id);
}