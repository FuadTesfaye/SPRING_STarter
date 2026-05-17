package com.eventdriven.inventoryservice.domain.service;

import com.eventdriven.inventoryservice.domain.model.Inventory;
import com.eventdriven.inventoryservice.domain.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryDomainService {
    
    private final InventoryRepository inventoryRepository;
    
    public InventoryDomainService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    
    public boolean checkStock(String product, Integer quantity) {
        return inventoryRepository.findByProduct(product)
                .map(inventory -> inventory.getQuantity() >= quantity)
                .orElse(false);
    }
    
    public void reserveStock(String product, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProduct(product)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
    }
}