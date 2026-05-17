package com.example.inventory.presentation.controller;

import com.example.inventory.application.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory", description = "Inventory management endpoints")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Operation(summary = "Update inventory for a user",
        responses = {
            @ApiResponse(responseCode = "200", description = "Inventory updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
        })
    @PostMapping("/update")
    public String updateInventory(@RequestParam(name = "username") String username) {
        inventoryService.updateInventory(username);
        return "Inventory updated for: " + username;
    }

    @Operation(summary = "Check inventory service status")
    @GetMapping
    public String status() {
        return "Inventory Service Running";
    }
}
