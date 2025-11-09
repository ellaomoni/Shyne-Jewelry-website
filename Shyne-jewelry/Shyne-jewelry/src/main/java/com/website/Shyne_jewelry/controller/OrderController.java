package com.website.Shyne_jewelry.controller;


import com.website.Shyne_jewelry.Service.OrderService;
import com.website.Shyne_jewelry.entities.Order;
import com.website.Shyne_jewelry.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor

public class OrderController {
    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@RequestParam String sessionId) {
        return ResponseEntity.ok(orderService.createOrder(sessionId));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(@RequestParam String sessionId) {
        return ResponseEntity.ok(orderService.getOrdersBySessionId(sessionId));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
}
