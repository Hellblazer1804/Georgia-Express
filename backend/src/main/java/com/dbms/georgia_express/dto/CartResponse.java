package com.dbms.georgia_express.dto;

import com.dbms.georgia_express.model.Cart;
import com.dbms.georgia_express.model.CartItem;

import java.util.List;
import java.util.stream.Collectors;

public class CartResponse {
    private List<CartItemDTO> cartItems;

    public CartResponse(List<CartItem> cartItems) {
        this.cartItems = cartItems.stream()
                .map(CartItemDTO::new)
                .collect(Collectors.toList());
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }
}
