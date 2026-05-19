package com.example.shippingservice.dto;

import lombok.Data;

@Data
public class PaymentEvent {
    private Long orderId;
    private String status;
}