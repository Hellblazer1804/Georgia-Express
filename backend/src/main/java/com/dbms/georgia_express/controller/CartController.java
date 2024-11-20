package com.dbms.georgia_express.controller;

import com.dbms.georgia_express.dto.AddtoCartRequest;
import com.dbms.georgia_express.dto.CartResponse;
import com.dbms.georgia_express.model.Cart;
import com.dbms.georgia_express.model.CartItem;
import com.dbms.georgia_express.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Void> addCart(@RequestBody AddtoCartRequest request, @RequestHeader String username) {
        cartService.addToCart(username, request.getItemId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long itemId, @RequestHeader String username) {
        cartService.removeFromCart(username, itemId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestHeader String username) {
        List<CartItem> cartItems = cartService.getCart(username);
        return ResponseEntity.ok(new CartResponse(cartItems));
    }
}

