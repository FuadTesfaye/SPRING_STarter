package com.example.shippingservice.dto;

import lombok.Data;

@Data
public class InventoryEvent {
    private Long orderId;
    private String status;
}