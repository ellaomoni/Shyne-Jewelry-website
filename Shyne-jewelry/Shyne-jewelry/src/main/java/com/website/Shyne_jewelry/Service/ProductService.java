package com.website.Shyne_jewelry.Service;

import com.website.Shyne_jewelry.dto.ProductsDTO;
import com.website.Shyne_jewelry.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Products createProduct(ProductsDTO productsdto);
    Products updateProduct(Long id, ProductsDTO productsDTO);
    void deleteProduct(Long id);
    Products getProductsById(Long id);
    Page<Products> getAllProducts(Pageable pageable);


}
