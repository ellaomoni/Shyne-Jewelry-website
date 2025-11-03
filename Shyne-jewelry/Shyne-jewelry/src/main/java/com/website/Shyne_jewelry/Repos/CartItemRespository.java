package com.website.Shyne_jewelry.Repos;

import com.website.Shyne_jewelry.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRespository extends JpaRepository<CartItem, Long> {


}
