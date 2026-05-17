package com.example.paymentservice.domain.entities;

import com.example.paymentservice.domain.enums.PaymentStatus;

public class Payment {

    private final String paymentId;
    private final String orderId;
    private final Long productId;
    private final int quantity;
    private final double amount;
    private final PaymentStatus status;

    public Payment(String paymentId, String orderId, Long productId, int quantity, double amount, PaymentStatus status) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
        this.status = status;
    }

    public String paymentId() {
        return paymentId;
    }

    public String orderId() {
        return orderId;
    }

    public Long productId() {
        return productId;
    }

    public int quantity() {
        return quantity;
    }

    public double amount() {
        return amount;
    }

    public PaymentStatus status() {
        return status;
    }
}
