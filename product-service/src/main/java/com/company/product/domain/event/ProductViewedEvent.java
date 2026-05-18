package com.company.product.domain.event;

import java.time.LocalDateTime;

public class ProductViewedEvent {
    private String productId;
    private String userId;
    private String category;
    private LocalDateTime timestamp;

    public ProductViewedEvent() {}

    public ProductViewedEvent(String productId, String userId, String category) {
        this.productId = productId;
        this.userId = userId;
        this.category = category;
        this.timestamp = LocalDateTime.now();
    }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
