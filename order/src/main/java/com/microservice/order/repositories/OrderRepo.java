package com.microservice.order.repositories;

import com.microservice.order.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    Page<Order> findByUserId(Long id, Pageable pageable);
    
    Optional<Order> findByUserIdAndShowtimeId(Long userId, Long showtimeId);
}
