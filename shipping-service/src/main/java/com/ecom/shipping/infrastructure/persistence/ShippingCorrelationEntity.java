package com.ecom.shipping.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "shipping_correlations")
public class ShippingCorrelationEntity {
    @Id
    private UUID orderId;
    private boolean paymentCompleted;
    private boolean stockReserved;

    public ShippingCorrelationEntity() {}

    public ShippingCorrelationEntity(UUID orderId, boolean paymentCompleted, boolean stockReserved) {
        this.orderId = orderId;
        this.paymentCompleted = paymentCompleted;
        this.stockReserved = stockReserved;
    }

    // Getters and Setters
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public boolean isPaymentCompleted() { return paymentCompleted; }
    public void setPaymentCompleted(boolean paymentCompleted) { this.paymentCompleted = paymentCompleted; }
    public boolean isStockReserved() { return stockReserved; }
    public void setStockReserved(boolean stockReserved) { this.stockReserved = stockReserved; }
}
