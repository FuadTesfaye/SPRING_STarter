package com.ecommerce.inventory.presentation.controller;

import com.ecommerce.inventory.application.service.InventoryService;
import com.ecommerce.inventory.domain.model.Inventory;
import com.ecommerce.inventory.presentation.dto.AddStockRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory", description = "Inventory management endpoints")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @Operation(summary = "Get all inventory items")
    public ResponseEntity<List<Inventory>> getAll() {
        return ResponseEntity.ok(inventoryService.findAll());
    }

    @PostMapping("/stock")
    @Operation(summary = "Add stock for a product")
    public ResponseEntity<Inventory> addStock(@RequestBody AddStockRequest req) {
        return ResponseEntity.ok(inventoryService.addStock(req.getProductId(), req.getQuantity()));
    }
}
