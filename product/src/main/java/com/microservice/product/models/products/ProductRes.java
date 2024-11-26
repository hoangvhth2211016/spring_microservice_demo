package com.microservice.product.models.products;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRes {
    
    private Long id;
    
    private String title;
    
    private String description;
    
    private BigDecimal price;
    
    private Long stock;
    
}
