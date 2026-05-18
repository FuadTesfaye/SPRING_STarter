package com.company.product.domain.event;

import java.time.LocalDateTime;

public class ShadeSelectedEvent {
    private String productId;
    private String shadeCode;
    private String userId;
    private LocalDateTime timestamp;

    public ShadeSelectedEvent() {}

    public ShadeSelectedEvent(String productId, String shadeCode, String userId) {
        this.productId = productId;
        this.shadeCode = shadeCode;
        this.userId = userId;
        this.timestamp = LocalDateTime.now();
    }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getShadeCode() { return shadeCode; }
    public void setShadeCode(String shadeCode) { this.shadeCode = shadeCode; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
