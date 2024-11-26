package com.microservice.product.models.inventories;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
public class InventoryRes {

    private Long id;
    
    private Long productId;
    
    private Long stock;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
