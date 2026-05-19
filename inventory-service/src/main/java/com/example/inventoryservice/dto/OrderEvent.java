package com.example.inventoryservice.dto;

import lombok.Data;

@Data
public class OrderEvent {
    private Long orderId;
    private String productName;
    private int quantity;
    private double price;
    private String status;
}