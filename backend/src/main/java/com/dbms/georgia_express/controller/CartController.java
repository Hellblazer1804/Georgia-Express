package com.dbms.georgia_express.controller;

import com.dbms.georgia_express.dto.AddtoCartRequest;
import com.dbms.georgia_express.dto.CartResponse;
import com.dbms.georgia_express.model.CartItem;
import com.dbms.georgia_express.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestHeader("Authorization") String token, @RequestBody AddtoCartRequest addtoCartRequest) {
        cartService.addToCart(token, addtoCartRequest.getItemId(), addtoCartRequest.getQuantity());
        return ResponseEntity.ok("Item added to cart");
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<?> removeFromCart(@RequestHeader("Authorization") String token, @PathVariable Long itemId) {
        cartService.removeFromCart(token, itemId);
        return ResponseEntity.ok("Item removed from cart");
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestHeader("Authorization") String token) {
        List<CartItem> cartItems = cartService.getCart(token);
        BigDecimal total = cartService.getTotalAmount(cartItems);
        return ResponseEntity.ok(new CartResponse(cartItems,total));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@RequestHeader("Authorization") String token) {
        cartService.clearCart(token);
        return ResponseEntity.ok("Cart cleared");
    }
}