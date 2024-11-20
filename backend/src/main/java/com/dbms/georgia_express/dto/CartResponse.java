package com.dbms.georgia_express.dto;

import com.dbms.georgia_express.model.Cart;
import com.dbms.georgia_express.model.CartItem;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CartResponse {
    private List<CartItemDTO> cartItems;

    @JsonProperty("cart_amount")
    private BigDecimal cartAmount;


    public CartResponse(List<CartItem> cartItems, BigDecimal cartAmount) {
        this.cartItems = cartItems.stream()
                .map(CartItemDTO::new)
                .collect(Collectors.toList());
        this.cartAmount = cartAmount;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public BigDecimal getCartAmount() {
        return cartAmount;
    }
}
