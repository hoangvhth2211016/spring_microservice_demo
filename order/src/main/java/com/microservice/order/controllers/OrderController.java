package com.microservice.order.controllers;


import com.microservice.order.entities.Order;
import com.microservice.order.entities.Ticket;
import com.microservice.order.models.pages.PageRes;
import com.microservice.order.models.users.UserInformation;
import com.microservice.order.services.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping
    public PageRes<Order> getAll(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return new PageRes<>(orderService.getAll(pageable));
    }
    
    @GetMapping("{id}")
    public Order getById(@PathVariable Long id){
        return orderService.getById(id);
    }
    
    @GetMapping("public/{orderId}/tickets")
    public List<Ticket> getTicketsByOrderId(@PathVariable Long orderId){
        return orderService.getTicketsByOrderId(orderId);
    }
    
    @PostMapping
    public String create(@AuthenticationPrincipal UserInformation userInfo){
        return orderService.create(userInfo);
    }
    
    @GetMapping("users")
    public PageRes<Order> getByUserId(
            @AuthenticationPrincipal UserInformation userInfo,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return new PageRes<>(orderService.getByUserId(userInfo.getId(), pageable));
    }
}
