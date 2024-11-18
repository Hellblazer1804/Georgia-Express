package com.dbms.georgia_express.service;

import com.dbms.georgia_express.dto.InventoryRequest;
import com.dbms.georgia_express.model.Inventory;
import com.dbms.georgia_express.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepo;

    public List<Inventory> getAllInventory() {
        return inventoryRepo.findAll();
    }

    public Optional<Inventory> findItemById(int id) {
        return inventoryRepo.findById(id);
    }

    public Inventory createInventory(InventoryRequest request) {
        Inventory inventory = new Inventory(request.getItemName(),request.getCost());
        return inventoryRepo.save(inventory);
    }

    public Inventory updateInventory(int itemId, InventoryRequest inventoryDetails) {
        Inventory inventory = inventoryRepo.findById(itemId).
                orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));

        inventory.setCost(inventoryDetails.getCost());
        inventory.setItemName(inventoryDetails.getItemName());

        return inventoryRepo.save(inventory);
    }

    public void deleteInventory(int itemId) {
        Inventory inventory = inventoryRepo.findById(itemId).
                orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));

        inventoryRepo.delete(inventory);
    }
}
