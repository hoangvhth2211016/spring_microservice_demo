package com.microservice.inventory.services;

import com.microservice.inventory.entities.Inventory;
import com.microservice.inventory.models.carts.Cart;
import com.microservice.inventory.models.inventories.InventoryCreateReq;
import com.microservice.inventory.models.inventories.StockUpdateReq;

public interface InventoryService {
    
    Inventory getById(Long id);
    
    Inventory create(InventoryCreateReq req);
    
    Inventory updateStock(Long id, StockUpdateReq req);
    
    void reserveForOrder(Cart cart);
}
