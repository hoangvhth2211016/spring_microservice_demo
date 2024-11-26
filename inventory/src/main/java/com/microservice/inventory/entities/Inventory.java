package com.microservice.inventory.entities;

import com.microservice.inventory.exceptions.BadRequestException;
import com.microservice.inventory.exceptions.OutOfStockException;
import com.microservice.inventory.models.inventories.StockFlow;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "inventories")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "product_id", nullable = false, unique = true)
    private Long productId;
    
    @Column(name = "stock", nullable = false)
    private Long stock;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    public void updateStock(StockFlow flow, Long quantity){
        switch (flow){
            case StockFlow.IN -> {
                setStock(this.stock + quantity);
            }
            case StockFlow.OUT -> {
                long res = this.stock - quantity;
                if(res < 0) {
                    throw new OutOfStockException();
                }
                setStock(res);
            }
            default -> {
                throw new BadRequestException("Unrecognized Request");
            }
        }
    }
    
}