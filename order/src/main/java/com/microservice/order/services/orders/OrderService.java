package com.microservice.order.services.orders;

import com.microservice.order.entities.Order;
import com.microservice.order.entities.Ticket;
import com.microservice.order.models.orders.OrderStatus;
import com.microservice.order.models.orders.OrderUpdateMessage;
import com.microservice.order.models.users.UserInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Page<Order> getAll(Pageable pageable);
    
    String create(UserInformation userInfo);
    
    Order getById(Long id);
    
    Order getByShowtimeIdAndUserId(Long showtimeId, Long userId);
    
    Order updateOrderStatus(Long orderId, OrderStatus status);
    
    Page<Order> getByUserId(Long id, Pageable pageable);
    
    List<Ticket> getTicketsByOrderId(Long id);
}
