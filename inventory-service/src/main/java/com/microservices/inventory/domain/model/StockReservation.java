package com.microservices.inventory.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class StockReservation {
    private String id;
    private String orderId;
    private String productId;
    private String productName;
    private int quantity;
    private StockStatus status;
    private String reason;
    private LocalDateTime processedAt;
    
    private StockReservation() {}
    
    public static StockReservation reserve(String orderId, String productId, 
                                            String productName, int quantity) {
        StockReservation sr = new StockReservation();
        sr.id = UUID.randomUUID().toString();
        sr.orderId = orderId;
        sr.productId = productId;
        sr.productName = productName;
        sr.quantity = quantity;
        sr.status = StockStatus.RESERVED;
        sr.processedAt = LocalDateTime.now();
        return sr;
    }
    
    public static StockReservation fail(String orderId, String productId,
                                         String productName, int quantity, String reason) {
        StockReservation sr = new StockReservation();
        sr.id = UUID.randomUUID().toString();
        sr.orderId = orderId;
        sr.productId = productId;
        sr.productName = productName;
        sr.quantity = quantity;
        sr.status = StockStatus.FAILED;
        sr.reason = reason;
        sr.processedAt = LocalDateTime.now();
        return sr;
    }
    
    public static StockReservation reconstruct(String id, String orderId, String productId,
                                                String productName, int quantity,
                                                StockStatus status, String reason,
                                                LocalDateTime processedAt) {
        StockReservation sr = new StockReservation();
        sr.id = id;
        sr.orderId = orderId;
        sr.productId = productId;
        sr.productName = productName;
        sr.quantity = quantity;
        sr.status = status;
        sr.reason = reason;
        sr.processedAt = processedAt;
        return sr;
    }
    
    public String getId() { return id; }
    public String getOrderId() { return orderId; }
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public StockStatus getStatus() { return status; }
    public String getReason() { return reason; }
    public LocalDateTime getProcessedAt() { return processedAt; }
}
