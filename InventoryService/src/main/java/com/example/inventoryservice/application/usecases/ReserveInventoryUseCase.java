package com.example.inventoryservice.application.usecases;

import com.example.inventoryservice.application.dto.request.InventoryReserveRequest;
import com.example.inventoryservice.application.dto.response.InventoryUpdateResponse;
import com.example.inventoryservice.application.usecases.ReserveInventoryService;
import com.example.inventoryservice.domain.entities.InventoryItem;
import com.example.inventoryservice.domain.interfaces.InventoryRepository;
import com.example.inventoryservice.domain.services.InventoryDomainService;

public class ReserveInventoryUseCase implements ReserveInventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryDomainService inventoryDomainService;

    public ReserveInventoryUseCase(
            InventoryRepository inventoryRepository,
            InventoryDomainService inventoryDomainService
    ) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryDomainService = inventoryDomainService;
    }

    public InventoryUpdateResponse execute(InventoryReserveRequest request) {
        validate(request);

        InventoryItem item = inventoryRepository.findByProductId(request.productId())
                .orElse(new InventoryItem(request.productId(), 0));

        if (item.availableQuantity() < request.quantity()) {
            return new InventoryUpdateResponse(
                    request.productId(),
                    item.availableQuantity(),
                    "INSUFFICIENT_STOCK",
                    "Not enough stock available"
            );
        }

        InventoryItem updated = inventoryRepository.save(inventoryDomainService.reserve(item, request.quantity()));
        return new InventoryUpdateResponse(
                updated.productId(),
                updated.availableQuantity(),
                "INVENTORY_UPDATED",
                "Inventory updated successfully"
        );
    }

    private void validate(InventoryReserveRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request body is required");
        }
        if (request.productId() == null) {
            throw new IllegalArgumentException("productId is required");
        }
        if (request.quantity() == null || request.quantity() <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
    }
}
