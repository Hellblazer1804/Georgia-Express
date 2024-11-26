package com.dbms.georgia_express.dto;

import com.dbms.georgia_express.model.CartItem;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemDTO {
    @JsonProperty("item_id")
    private Long itemId;
    @JsonProperty("item_name")
    private String itemName;
    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("cart_item_cost")
    private Integer cartItemCost;

    public CartItemDTO(CartItem cartItem) {
        this.itemId = (long) cartItem.getItem().getItemId();
        this.itemName = cartItem.getItem().getItemName();
        this.quantity = cartItem.getQuantity();
        this.cartItemCost = cartItem.getCost();
    }

    // Getters
}

