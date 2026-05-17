package com.microservices.inventory.domain.event;

import java.time.LocalDateTime;

public class StockFailedEvent {
    private String reservationId;
    private String orderId;
    private String productId;
    private String productName;
    private int quantity;
    private String reason;
    private LocalDateTime failedAt;
    
    public StockFailedEvent() {}
    
    public StockFailedEvent(String reservationId, String orderId, String productId,
                             String productName, int quantity, String reason) {
        this.reservationId = reservationId;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.reason = reason;
        this.failedAt = LocalDateTime.now();
    }
    
    public String getReservationId() { return reservationId; }
    public void setReservationId(String reservationId) { this.reservationId = reservationId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public LocalDateTime getFailedAt() { return failedAt; }
    public void setFailedAt(LocalDateTime failedAt) { this.failedAt = failedAt; }
}
