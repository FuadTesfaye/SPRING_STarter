package com.assignment.inventory.presentation.controller;


import com.assignment.inventory.application.dto.StockCheckRequest;
import com.assignment.inventory.application.dto.StockResponse;
import com.assignment.inventory.application.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    
    private final InventoryService inventoryService;
    
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    
    @PostMapping("/check")
    public ResponseEntity<StockResponse> checkStock(@RequestBody StockCheckRequest request) {
        StockResponse response = inventoryService.checkStock(request);
        return ResponseEntity.ok(response);
    }
}
