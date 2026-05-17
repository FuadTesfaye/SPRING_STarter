package com.example.inventoryservice.domain.entities;

public class InventoryItem {

    private final Long productId;
    private final int availableQuantity;

    public InventoryItem(Long productId, int availableQuantity) {
        this.productId = productId;
        this.availableQuantity = availableQuantity;
    }

    public Long productId() {
        return productId;
    }

    public int availableQuantity() {
        return availableQuantity;
    }

    public InventoryItem withAvailableQuantity(int newQuantity) {
        return new InventoryItem(productId, newQuantity);
    }
}
