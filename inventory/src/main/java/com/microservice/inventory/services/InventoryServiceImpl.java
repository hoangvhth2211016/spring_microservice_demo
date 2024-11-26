package com.microservice.inventory.services;

import com.microservice.inventory.entities.Inventory;
import com.microservice.inventory.exceptions.BadRequestException;
import com.microservice.inventory.exceptions.NotFoundException;
import com.microservice.inventory.exceptions.OutOfStockException;
import com.microservice.inventory.models.carts.Cart;
import com.microservice.inventory.models.inventories.InventoryCreateReq;
import com.microservice.inventory.models.inventories.StockFlow;
import com.microservice.inventory.models.inventories.StockUpdateReq;
import com.microservice.inventory.repositories.InventoryRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService{
    
    @Autowired
    private InventoryRepo inventoryRepo;
    
    @Override
    public Inventory getById(Long id) {
        return inventoryRepo.findById(id).orElseThrow(() -> new NotFoundException("Inventory not found"));
    }
    
    @Override
    public Inventory create(InventoryCreateReq req) {
        Inventory inventory = new Inventory();
        inventory.setProductId(req.getProductId());
        inventory.setStock(req.getStock());
        return inventoryRepo.save(inventory);
    }
    
    @Override
    public Inventory updateStock(Long id, StockUpdateReq req) {
        Inventory inventory = getById(id);
        inventory.updateStock(req.getFlow(), req.getQuantity());
        return inventoryRepo.save(inventory);
    }
    
    @Override
    public void reserveForOrder(Cart cart) {
        List<Inventory> inventories = inventoryRepo.findByProductIdIn(cart.getProducts().keySet());
        for(Inventory inventory: inventories){
            inventory.updateStock(StockFlow.OUT, Long.valueOf(cart.getProducts().get(inventory.getProductId())));
        }
        inventoryRepo.saveAll(inventories);
    }
    
}
