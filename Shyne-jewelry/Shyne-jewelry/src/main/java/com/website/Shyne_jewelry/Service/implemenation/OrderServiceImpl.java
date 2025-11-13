package com.website.Shyne_jewelry.Service.implemenation;


import com.website.Shyne_jewelry.Repos.CartRepository;
import com.website.Shyne_jewelry.Repos.OrderRepository;
import com.website.Shyne_jewelry.Repos.TransactionRepository;
import com.website.Shyne_jewelry.Service.OrderService;
import com.website.Shyne_jewelry.Service.PaymentService;
import com.website.Shyne_jewelry.dto.CheckoutResponseDTO;
import com.website.Shyne_jewelry.dto.PaymentInitResponseDTO;
import com.website.Shyne_jewelry.entities.*;
import com.website.Shyne_jewelry.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private PaymentService paymentService;

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final TransactionRepository transactionRepository;

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

    public CheckoutResponseDTO checkout(String sessionId) {
        Cart cart = cartRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        double totalAmount = cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Step 1: Create pending order
        Order order = new Order();
        order.setSessionId(sessionId);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        // Step 2: Initialize Paystack payment
        PaymentInitResponseDTO paymentInit = paymentService.initializePayment(
                "testemail@example.com", // or user's email
                totalAmount,
                sessionId
        );

        // Step 3: Save transaction reference
        Transaction tx = new Transaction();
        tx.setReference(paymentInit.getReference());
        tx.setAmount(totalAmount);
        tx.setEmail("testemail@example.com");
        tx.setStatus("PENDING");
        tx.setSessionId(sessionId);
        transactionRepository.save(tx);

        // Link transaction to order
        order.setTransaction(tx);
        orderRepository.save(order);

        // Step 4: Return payment link
        return new CheckoutResponseDTO(paymentInit.getAuthorizationUrl(), tx.getReference());
    }


}
