package com.dbms.georgia_express.repositories;

import com.dbms.georgia_express.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> findInventoryByItemName(String itemName);
}
