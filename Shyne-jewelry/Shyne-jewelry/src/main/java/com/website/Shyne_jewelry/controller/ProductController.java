package com.website.Shyne_jewelry.controller;


import com.website.Shyne_jewelry.Service.ProductService;
import com.website.Shyne_jewelry.dto.ProductsDTO;
import com.website.Shyne_jewelry.entities.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    // ✅ Create (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public Products createProducts (@RequestBody ProductsDTO productsDTO) {
        return productService.createProduct(productsDTO);
    }

    // ✅ Update (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Products updateProducts(@PathVariable Long id, @RequestBody ProductsDTO productsDTO) {
        return productService.updateProduct(id, productsDTO);
    }

    // ✅ Delete (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProducts (@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    // ✅ View all products (Public)
    @GetMapping
    public Page<Products> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.getAllProducts(PageRequest.of(page, size));
    }

    //  View single product (Public)
    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductsById(@PathVariable Long id) {
        Products products = productService.getProductsById(id);
        return ResponseEntity.ok(products);
    }


}
