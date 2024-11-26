package com.microservice.inventory.models.carts;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Cart {
    
    private Long userId;
    
    private Map<Long, Integer> products = new HashMap<>();
    
}
