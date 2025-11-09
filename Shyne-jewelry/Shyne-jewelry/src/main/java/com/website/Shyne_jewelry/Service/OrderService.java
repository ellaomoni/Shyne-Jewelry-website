package com.website.Shyne_jewelry.Service;

import com.website.Shyne_jewelry.entities.Order;
import com.website.Shyne_jewelry.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(String sessionId);
    List<Order> getOrdersBySessionId(String sessionId);
    Order updateOrderStatus(Long orderId, OrderStatus status);
}
