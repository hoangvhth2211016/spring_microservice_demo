package com.microservice.product.models.products;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreateReq {
    
    @NotBlank
    private String title;
    
    @NotBlank
    private String description;
    
    @NotNull
    private BigDecimal price;
    
    @Min(0)
    @NotNull
    private Integer stock = 0;
}
