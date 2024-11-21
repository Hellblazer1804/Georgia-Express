package com.dbms.georgia_express.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "cost")
    private Integer cost;

    public Inventory(String itemName, int cost) {
        this.itemName = itemName;
        this.cost = cost;
    }

    public Inventory() {

    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getCost() {
        return cost;
    }
}
