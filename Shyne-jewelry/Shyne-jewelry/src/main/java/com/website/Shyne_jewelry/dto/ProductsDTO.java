package com.website.Shyne_jewelry.dto;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductsDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private String category;
    private  String imageUrl;
    private boolean isAvailable;

}
