package com.microservice.product.controllers;

import com.microservice.product.entities.Product;
import com.microservice.product.models.inventories.InventoryCreateReq;
import com.microservice.product.models.inventories.InventoryRes;
import com.microservice.product.models.pages.PageRes;
import com.microservice.product.models.products.ProductCreateReq;
import com.microservice.product.models.products.ProductRes;
import com.microservice.product.services.products.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private WebClient webClient;
    
    @GetMapping
    public PageRes<Product> getAll(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return new PageRes<Product>(productService.getAll(pageable));
    }
    
    @GetMapping("{id}")
    public Product getById(@PathVariable Long id){
        return productService.getById(id);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ProductRes create(@RequestBody @Valid ProductCreateReq req){
        Product product = productService.create(req);
        InventoryCreateReq inventoryCreateReq = new InventoryCreateReq();
        inventoryCreateReq.setProductId(product.getId());
        inventoryCreateReq.setStock(req.getStock().longValue());
        InventoryRes inventory = webClient
                .post()
                .uri("/inventories")
                .bodyValue(inventoryCreateReq)
                .retrieve()
                .bodyToMono(InventoryRes.class)
                .block();
        //if(inventory == null){
        //    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create inventory");
        //}
        return ProductRes.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(inventory.getStock())
                .build();
    }
    
}
