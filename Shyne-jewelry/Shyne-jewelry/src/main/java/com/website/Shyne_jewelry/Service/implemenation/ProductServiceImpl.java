package com.website.Shyne_jewelry.Service.implemenation;

import com.website.Shyne_jewelry.Repos.ProductRepository;
import com.website.Shyne_jewelry.Service.ProductService;
import com.website.Shyne_jewelry.entities.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl  implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Products createProduct(Products products) {
        return productRepository.save(products);
    }

    @Override
    public Products updateProduct(Long id, Products products) {
        Products existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(products.getName());
        existing.setDescription(products.getDescription());
        existing.setPrice(products.getPrice());
        existing.setCategory(products.getCategory());
        existing.setStockQuantity(products.getStockQuantity());
        existing.setImageUrl(products.getImageUrl());
        existing.setAvailable(products.isAvailable());

        return productRepository.save(existing);
    }

    @Override
    public void deleteProduct(Long id) {
         productRepository.deleteById(id);
    }

    @Override
    public Products getProductsById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found."));
    }

    @Override
    public Page<Products> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
