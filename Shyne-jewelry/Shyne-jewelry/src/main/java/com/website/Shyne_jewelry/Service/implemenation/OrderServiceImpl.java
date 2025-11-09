package com.website.Shyne_jewelry.Service.implemenation;


import com.website.Shyne_jewelry.Repos.CartRepository;
import com.website.Shyne_jewelry.Repos.OrderRepository;
import com.website.Shyne_jewelry.Service.OrderService;
import com.website.Shyne_jewelry.entities.Cart;
import com.website.Shyne_jewelry.entities.CartItem;
import com.website.Shyne_jewelry.entities.Order;
import com.website.Shyne_jewelry.entities.OrderItems;
import com.website.Shyne_jewelry.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Override
    public Order createOrder(String sessionId) {
        Cart cart = cartRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setSessionId(sessionId);
        order.setOrderStatus(OrderStatus.PENDING);
        //order.setStatus(OrderStatus.PENDING);

        double total = 0;
        for (CartItem item : cart.getItems()) {
            OrderItems orderItem = new OrderItems();
            orderItem.setProductId(item.getProductId());
            orderItem.setProductName(item.getProductName());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrder(order);

            order.getItems().add(orderItem);
            total += item.getPrice() * item.getQuantity();
        }

        order.setTotalPrice(total);
        cart.getItems().clear(); // Empty the cart after checkout
        cartRepository.save(cart);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersBySessionId(String sessionId) {
        return orderRepository.findBySessionId(sessionId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.getOrderStatus();
        //order.setStatus(status);
        return orderRepository.save(order);
    }

}
