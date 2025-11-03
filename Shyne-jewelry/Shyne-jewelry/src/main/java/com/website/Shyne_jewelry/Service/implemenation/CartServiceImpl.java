package com.website.Shyne_jewelry.Service.implemenation;

import com.website.Shyne_jewelry.Repos.CartItemRespository;
import com.website.Shyne_jewelry.Repos.CartRepository;
import com.website.Shyne_jewelry.Service.CartService;
import com.website.Shyne_jewelry.entities.Cart;
import com.website.Shyne_jewelry.entities.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRespository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRespository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart addToCart(String sessionId, Long productId, String productName, Double price, int quantity) {
        Cart cart = cartRepository.findBySessionId(sessionId)
                .orElseGet(() -> cartRepository.save(new Cart(sessionId)));

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem item = new CartItem(productId, productName, price, quantity);
            item.setCart(cart);
            cart.getItems().add(item);
        }

        return cartRepository.save(cart);
    }

    @Override
    public Cart updateQuantity(String sessionId, Long itemId, int quantity) {
        Cart cart = cartRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().forEach(i -> {
            if (i.getId().equals(itemId)) {
                i.setQuantity(quantity);
            }
        });
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeFromCart(String sessionId, Long itemId) {
        Cart cart = cartRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().removeIf(i -> i.getId().equals(itemId));
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCart(String sessionId) {
        return cartRepository.findBySessionId(sessionId)
                .orElseGet(() -> cartRepository.save(new Cart(sessionId)));
    }

    @Override
    public void clearCart(String sessionId) {
        Cart cart = cartRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }



}
