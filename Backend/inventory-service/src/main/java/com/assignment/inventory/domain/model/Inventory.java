package com.assignment.inventory.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Inventory {
    private UUID id;
    private String productName;
    private int availableStock;
    private Instant updatedAt;

    public Inventory() {}

    public Inventory(UUID id, String productName, int availableStock, Instant updatedAt) {
        this.id = id; this.productName = productName;
        this.availableStock = availableStock; this.updatedAt = updatedAt;
    }

    public static Inventory create(String productName, int stock) {
        return new Inventory(UUID.randomUUID(), productName, stock, Instant.now());
    }

    public UUID getId() { return id; }
    public String getProductName() { return productName; }
    public int getAvailableStock() { return availableStock; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setAvailableStock(int stock) { this.availableStock = stock; this.updatedAt = Instant.now(); }
}
