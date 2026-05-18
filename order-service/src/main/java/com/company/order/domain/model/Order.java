package com.company.order.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private Long id;
    private String customerId;
    private String productId;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private String status;
    private LocalDateTime createdAt;

    public Order() {}

    public Order(Long id, String customerId, String productId, Integer quantity, BigDecimal pricePerUnit, String status, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Order(String customerId, String productId, Integer quantity, BigDecimal pricePerUnit) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getCustomerId() { return customerId; }
    public String getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getPricePerUnit() { return pricePerUnit; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setProductId(String productId) { this.productId = productId; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setPricePerUnit(BigDecimal pricePerUnit) { this.pricePerUnit = pricePerUnit; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public BigDecimal getTotalPrice() {
        return pricePerUnit != null ? pricePerUnit.multiply(BigDecimal.valueOf(quantity)) : BigDecimal.ZERO;
    }
}
