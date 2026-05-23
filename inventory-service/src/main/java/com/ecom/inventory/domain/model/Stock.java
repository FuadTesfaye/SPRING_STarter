package com.ecom.inventory.domain.model;

import java.util.UUID;

public class Stock {
    private final UUID productId;
    private final String productName;
    private int quantity;

    public Stock(UUID productId, String productName, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public boolean hasEnoughStock(int requestedQuantity) {
        return this.quantity >= requestedQuantity;
    }

    public void reserve(int requestedQuantity) {
        if (!hasEnoughStock(requestedQuantity)) {
            throw new IllegalStateException("Not enough stock for product: " + productName);
        }
        this.quantity -= requestedQuantity;
    }

    // Getters
    public UUID getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
}
