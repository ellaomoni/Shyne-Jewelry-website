package com.website.Shyne_jewelry.Service;

import com.website.Shyne_jewelry.entities.Cart;

public interface CartService {

    Cart addToCart(String sessionId, Long productId, String productName, Double price, int quantity);
    Cart updateQuantity(String sessionId, Long itemId, int quantity);
    Cart removeFromCart(String sessionId, Long itemId);
    Cart getCart(String sessionId);
    void clearCart(String sessionId);
}
