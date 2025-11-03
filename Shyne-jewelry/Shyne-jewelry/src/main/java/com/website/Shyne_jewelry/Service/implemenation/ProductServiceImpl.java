package com.website.Shyne_jewelry.Service.implemenation;

import com.website.Shyne_jewelry.Repos.ProductRepository;
import com.website.Shyne_jewelry.Service.ProductService;
import com.website.Shyne_jewelry.dto.ProductsDTO;
import com.website.Shyne_jewelry.entities.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl  implements ProductService {

    @Autowired
    private ProductRepository productRepository;


//    @Override
//    public Products createProduct(Products products) {
//        return productRepository.save(products);
//    }

    @Override
    public Products createProduct(ProductsDTO productsDTO) {
        Products product = Products.builder()
                .name(productsDTO.getName())
                .description(productsDTO.getDescription())
                .price(productsDTO.getPrice())
                .stockQuantity(productsDTO.getStockQuantity())
                .category(productsDTO.getCategory())
                .imageUrl(productsDTO.getImageUrl())
                .isAvailable(productsDTO.isAvailable())
                .build();

        return productRepository.save(product);
    }


    @Override
    public Products updateProduct(Long id, ProductsDTO productsDTO) {
        Products existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(productsDTO.getName());
        existing.setDescription(productsDTO.getDescription());
        existing.setPrice(productsDTO.getPrice());
        existing.setCategory(productsDTO.getCategory());
        existing.setStockQuantity(productsDTO.getStockQuantity());
        existing.setImageUrl(productsDTO.getImageUrl());
        existing.setAvailable(productsDTO.isAvailable());

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
