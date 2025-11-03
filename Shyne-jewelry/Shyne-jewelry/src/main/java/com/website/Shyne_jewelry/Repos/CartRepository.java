package com.website.Shyne_jewelry.Repos;

import com.website.Shyne_jewelry.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findBySessionId (String sessionId);
}
