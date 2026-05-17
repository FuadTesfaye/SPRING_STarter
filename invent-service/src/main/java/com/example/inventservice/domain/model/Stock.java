package com.example.inventservice.domain.model;

import java.util.UUID;

public class Stock {
    private UUID id;
    private String productName;
    private int availableQuantity;
    private int reservedQuantity;

    public Stock() {}

    public Stock(String productName, int availableQuantity) {
        this.id = UUID.randomUUID();
        this.productName = productName;
        this.availableQuantity = availableQuantity;
        this.reservedQuantity = 0;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }

    public int getReservedQuantity() { return reservedQuantity; }
    public void setReservedQuantity(int reservedQuantity) { this.reservedQuantity = reservedQuantity; }
}