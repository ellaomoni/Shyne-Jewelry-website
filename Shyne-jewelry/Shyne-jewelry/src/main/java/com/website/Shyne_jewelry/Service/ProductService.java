package com.website.Shyne_jewelry.Service;

import com.website.Shyne_jewelry.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Products createProduct(Products products);
    Products updateProduct(Long id, Products products);
    void deleteProduct(Long id);
    Products getProductsById(Long id);
    Page<Products> getAllProducts(Pageable pageable);


}
