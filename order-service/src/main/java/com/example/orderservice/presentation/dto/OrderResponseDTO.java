package com.example.orderservice.presentation.dto;

import java.util.UUID;


public class OrderResponseDTO {

    private UUID id;
    private String productName;
    private int quantity;
    private String status;

    public OrderResponseDTO(UUID id, String productName, int quantity, String status) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.status = status;
    }

    public UUID getId() { return id; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public String getStatus() { return status; }
}
