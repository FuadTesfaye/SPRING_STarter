package com.example.task.inventory.model;

public class ProductStock {
    private String productId;
    private int availableQuantity;

    public ProductStock(String productId, int initialQuantity) {
        this.productId = productId;
        this.availableQuantity = initialQuantity;
    }

    public String getProductId() { return productId; }
    public int getAvailableQuantity() { return availableQuantity; }

    public boolean deductStock(int amount) {
        if (this.availableQuantity >= amount) {
            this.availableQuantity -= amount;
            return true;
        }
        return false;
    }

    public void addStock(int amount) {
        this.availableQuantity += amount;
    }
}
