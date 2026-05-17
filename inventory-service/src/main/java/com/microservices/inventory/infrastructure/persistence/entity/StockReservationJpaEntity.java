package com.microservices.inventory.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_reservations")
public class StockReservationJpaEntity {
    
    @Id
    private String id;
    @Column(name = "order_id", nullable = false)
    private String orderId;
    @Column(name = "product_id", nullable = false)
    private String productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "reason")
    private String reason;
    @Column(name = "processed_at", nullable = false)
    private LocalDateTime processedAt;
    
    public StockReservationJpaEntity() {}
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
}
