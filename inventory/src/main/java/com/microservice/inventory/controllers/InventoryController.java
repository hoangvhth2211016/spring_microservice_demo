package com.microservice.inventory.controllers;

import com.microservice.inventory.entities.Inventory;
import com.microservice.inventory.exceptions.BadRequestException;
import com.microservice.inventory.models.carts.Cart;
import com.microservice.inventory.models.inventories.InventoryCreateReq;
import com.microservice.inventory.models.inventories.StockFlow;
import com.microservice.inventory.models.inventories.StockUpdateReq;
import com.microservice.inventory.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    
    @GetMapping("{id}")
    public Inventory getById(@PathVariable Long id){
        return inventoryService.getById(id);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public Inventory create(@RequestBody InventoryCreateReq req){
        if(req.getStock() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create inventory");
        }
        return inventoryService.create(req);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("{id}")
    public Inventory updateStock(@PathVariable Long id, @RequestBody StockUpdateReq req){
        return inventoryService.updateStock(id, req);
    }
    
    @PutMapping("orders")
    public void reserveForOrder(@RequestBody Cart cart){
        inventoryService.reserveForOrder(cart);
    }
    
    
}
