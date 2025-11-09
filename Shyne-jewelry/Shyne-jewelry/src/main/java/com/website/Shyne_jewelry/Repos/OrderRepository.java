package com.website.Shyne_jewelry.Repos;

import com.website.Shyne_jewelry.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findBySessionId(String sessionId);


}
