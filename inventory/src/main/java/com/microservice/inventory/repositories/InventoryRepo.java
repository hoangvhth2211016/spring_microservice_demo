package com.microservice.inventory.repositories;

import com.microservice.inventory.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long> {
    
    
    List<Inventory> findByProductIdIn(Collection<Long> productIds);
}