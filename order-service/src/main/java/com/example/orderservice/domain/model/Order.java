package com.example.orderservice.domain.model;

import java.util.UUID;

public class Order {

    private UUID id;
    private String productName;
    private int quantity;
    private String status;

    public Order() {}

    public Order(UUID id, String productName, int quantity, String status) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.status = status;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
