package com.microservices.inventory.presentation.controller;

import com.microservices.inventory.application.dto.StockResponse;
import com.microservices.inventory.application.service.InventoryApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory", description = "Inventory query API")
public class InventoryController {
    
    private final InventoryApplicationService applicationService;
    
    public InventoryController(InventoryApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get reservation by ID")
    public ResponseEntity<StockResponse> getReservation(@PathVariable String id) {
        return ResponseEntity.ok(applicationService.getReservation(id));
    }
    
    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get reservations by order ID")
    public ResponseEntity<List<StockResponse>> getByOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(applicationService.getByOrderId(orderId));
    }
    
    @GetMapping
    @Operation(summary = "Get all reservations")
    public ResponseEntity<List<StockResponse>> getAll() {
        return ResponseEntity.ok(applicationService.getAll());
    }
    
    @GetMapping("/health")
    @Operation(summary = "Health check")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Inventory Service is running! 📦");
    }
}
