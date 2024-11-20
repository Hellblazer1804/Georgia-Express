package com.dbms.georgia_express.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddtoCartRequest {
    @JsonProperty("item_id")
    private int itemId;
    @JsonProperty("quantity")
    private int quantity;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
