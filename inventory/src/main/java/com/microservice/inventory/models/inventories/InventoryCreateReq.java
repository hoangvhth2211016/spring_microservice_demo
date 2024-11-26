package com.microservice.inventory.models.inventories;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryCreateReq {
    
    @NotNull
    private Long productId;
    
    @NotNull
    @Min(0)
    private Long stock;
    
}
