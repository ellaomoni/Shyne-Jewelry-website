package com.website.Shyne_jewelry.controller;


import com.website.Shyne_jewelry.Service.CartService;
import com.website.Shyne_jewelry.entities.Cart;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public Cart addToCart(@RequestParam String sessionId,
                          @RequestParam Long productId,
                          @RequestParam String productName,
                          @RequestParam Double price,
                          @RequestParam(defaultValue = "1") int quantity) {
        return cartService.addToCart(sessionId, productId, productName, price, quantity);
    }

    @PutMapping("/update")
    public Cart updateQuantity(@RequestParam String sessionId,
                               @RequestParam Long itemId,
                               @RequestParam int quantity) {
        return cartService.updateQuantity(sessionId, itemId, quantity);
    }

    @DeleteMapping("/remove")
    public Cart removeFromCart(@RequestParam String sessionId, @RequestParam Long itemId) {
        return cartService.removeFromCart(sessionId, itemId);
    }

    @GetMapping("/view")
    public Cart getCart(@RequestParam String sessionId) {
        return cartService.getCart(sessionId);
    }

    @DeleteMapping("/clear")
    public void clearCart(@RequestParam String sessionId) {
        cartService.clearCart(sessionId);
    }

}
