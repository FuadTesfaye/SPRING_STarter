package com.example.inventoryservice.application.usecases;

import com.example.inventoryservice.application.dto.response.InventoryStatusResponse;
import com.example.inventoryservice.application.handlers.ResourceNotFoundException;
import com.example.inventoryservice.application.usecases.GetInventoryService;
import com.example.inventoryservice.domain.entities.InventoryItem;
import com.example.inventoryservice.domain.interfaces.InventoryRepository;

public class GetInventoryUseCase implements GetInventoryService {

    private final InventoryRepository inventoryRepository;

    public GetInventoryUseCase(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public InventoryStatusResponse execute(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("productId is required");
        }

        InventoryItem item = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory record not found for productId " + productId));

        return new InventoryStatusResponse(item.productId(), item.availableQuantity(), "AVAILABLE");
    }
}
