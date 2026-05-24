package com.ecom.shipping.domain.model;

import java.util.UUID;

public class ShippingCorrelation {
    private final UUID orderId;
    private boolean paymentCompleted;
    private boolean stockReserved;

    public ShippingCorrelation(UUID orderId, boolean paymentCompleted, boolean stockReserved) {
        this.orderId = orderId;
        this.paymentCompleted = paymentCompleted;
        this.stockReserved = stockReserved;
    }

    public boolean isReadyForShipping() {
        return paymentCompleted && stockReserved;
    }

    // Getters and Setters
    public UUID getOrderId() { return orderId; }
    public boolean isPaymentCompleted() { return paymentCompleted; }
    public void setPaymentCompleted(boolean paymentCompleted) { this.paymentCompleted = paymentCompleted; }
    public boolean isStockReserved() { return stockReserved; }
    public void setStockReserved(boolean stockReserved) { this.stockReserved = stockReserved; }
}
