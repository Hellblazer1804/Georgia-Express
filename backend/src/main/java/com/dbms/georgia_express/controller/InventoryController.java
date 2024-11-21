package com.dbms.georgia_express.controller;

import com.dbms.georgia_express.dto.InventoryRequest;
import com.dbms.georgia_express.model.Inventory;
import com.dbms.georgia_express.service.InventoryService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Inventory> getItemyById(@PathVariable int itemId) {
        return inventoryService.findItemById(itemId).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Inventory createInventory(@RequestBody InventoryRequest request) {
        return inventoryService.createInventory(request);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable int itemId, @RequestBody InventoryRequest request) {
        Inventory updatedInventory = inventoryService.updateInventory(itemId, request);
        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Inventory> deleteInventory(@PathVariable int itemId) {
        inventoryService.deleteInventory(itemId);
        return ResponseEntity.ok().build();
    }
}
