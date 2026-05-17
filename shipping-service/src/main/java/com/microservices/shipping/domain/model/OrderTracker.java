package com.microservices.shipping.domain.model;

import java.time.LocalDateTime;

/**
 * ORDER TRACKER
 * 
 * WHAT: Tracks which events have been received for an order
 * WHY: Shipping needs BOTH payment.completed AND stock.reserved
 * 
 * SAGA PATTERN: This is the saga state tracker
 */
public class OrderTracker {
    private String orderId;
    private boolean paymentCompleted;
    private boolean stockReserved;
    private String paymentId;
    private String reservationId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private OrderTracker() {}
    
    /**
     * Create new tracker when first event arrives
     */
    public static OrderTracker create(String orderId) {
        OrderTracker tracker = new OrderTracker();
        tracker.orderId = orderId;
        tracker.paymentCompleted = false;
        tracker.stockReserved = false;
        tracker.createdAt = LocalDateTime.now();
        tracker.updatedAt = LocalDateTime.now();
        return tracker;
    }
    
    /**
     * Mark payment as completed
     */
    public void markPaymentCompleted(String paymentId) {
        this.paymentCompleted = true;
        this.paymentId = paymentId;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Mark stock as reserved
     */
    public void markStockReserved(String reservationId) {
        this.stockReserved = true;
        this.reservationId = reservationId;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Check if both conditions are met for shipping
     */
    public boolean isReadyToShip() {
        return paymentCompleted && stockReserved;
    }
    
    // Getters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public boolean isPaymentCompleted() { return paymentCompleted; }
    public void setPaymentCompleted(boolean paymentCompleted) { this.paymentCompleted = paymentCompleted; }
    public boolean isStockReserved() { return stockReserved; }
    public void setStockReserved(boolean stockReserved) { this.stockReserved = stockReserved; }
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getReservationId() { return reservationId; }
    public void setReservationId(String reservationId) { this.reservationId = reservationId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
