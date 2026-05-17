package com.example.inventory.presentation.controller;

import com.example.inventory.application.service.InventoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/update")
    public String updateInventory(@RequestParam String username) {
        inventoryService.updateInventory(username);
        return "Inventory updated for: " + username;
    }
}
