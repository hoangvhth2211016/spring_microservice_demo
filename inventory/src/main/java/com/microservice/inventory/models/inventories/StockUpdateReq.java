package com.microservice.inventory.models.inventories;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUpdateReq {
    
    @NotNull
    private StockFlow flow;
    
    @NotNull
    @Min(1)
    private Long quantity;
    
}
