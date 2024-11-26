package com.microservice.product.services.products;

import com.microservice.product.entities.Product;
import com.microservice.product.models.products.ProductCreateReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    
    Page<Product> getAll(Pageable pageable);
    
    Product create(ProductCreateReq req);
    
    Product getById(Long id);

}
