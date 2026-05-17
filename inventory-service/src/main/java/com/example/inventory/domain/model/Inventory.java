package com.example.inventory.domain.model;

import java.util.UUID;

public class Inventory {
    private UUID id;
    private UUID productId;
    private String productName;
    private int quantity;
    private int reservedQuantity;

    public Inventory(UUID productId, String productName, int quantity) {
        this.id = UUID.randomUUID();
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.reservedQuantity = 0;
    }

    public boolean canReserve(int requestedQuantity) {
        return (quantity - reservedQuantity) >= requestedQuantity;
    }

    public void reserve(int quantity) {
        this.reservedQuantity += quantity;
    }

    public UUID getId() { return id; }
    public UUID getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public int getReservedQuantity() { return reservedQuantity; }
    public int getAvailableQuantity() { return quantity - reservedQuantity; }
}