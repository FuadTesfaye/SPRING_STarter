package com.example.paymentservice.presentation.dto;


import java.util.UUID;

public class PaymentResponseDTO {

    private UUID orderId;
    private String status;

    public PaymentResponseDTO(UUID orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }
}
