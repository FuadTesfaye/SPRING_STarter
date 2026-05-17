package com.microservices.inventory.domain.event;

import java.time.LocalDateTime;

public class StockReservedEvent {
    private String reservationId;
    private String orderId;
    private String productId;
    private String productName;
    private int quantity;
    private LocalDateTime reservedAt;
    
    public StockReservedEvent() {}
    
    public StockReservedEvent(String reservationId, String orderId, 
                               String productId, String productName, int quantity) {
        this.reservationId = reservationId;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.reservedAt = LocalDateTime.now();
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
    public LocalDateTime getReservedAt() { return reservedAt; }
    public void setReservedAt(LocalDateTime reservedAt) { this.reservedAt = reservedAt; }
}
