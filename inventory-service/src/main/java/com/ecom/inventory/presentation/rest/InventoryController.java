package com.ecom.inventory.presentation.rest;

import com.ecom.inventory.domain.model.Stock;
import com.ecom.inventory.domain.repository.InventoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {
    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllInventory() {
        return ResponseEntity.ok(inventoryRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Stock> updateInventory(@RequestBody StockUpdateRequest request) {
        Stock stock = new Stock(request.getProductId(), request.getProductName(), request.getQuantity());
        Stock saved = inventoryRepository.save(stock);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveInventory(@RequestBody List<ReserveItemRequest> items) {
        try {
            for (ReserveItemRequest item : items) {
                Stock stock = inventoryRepository.findByProductId(item.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Product not found in inventory: " + item.getProductId()));
                stock.reserve(item.getQuantity());
                inventoryRepository.save(stock);
            }
            return ResponseEntity.ok("RESERVED");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("FAILED: " + e.getMessage());
        }
    }

    public static class StockUpdateRequest {
        private UUID productId;
        private String productName;
        private int quantity;

        public UUID getProductId() { return productId; }
        public void setProductId(UUID productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    public static class ReserveItemRequest {
        private UUID productId;
        private int quantity;

        public UUID getProductId() { return productId; }
        public void setProductId(UUID productId) { this.productId = productId; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }
}
