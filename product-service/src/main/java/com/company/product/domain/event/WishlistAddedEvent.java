package com.company.product.domain.event;

import java.time.LocalDateTime;

public class WishlistAddedEvent {
    private String productId;
    private String userId;
    private LocalDateTime timestamp;

    public WishlistAddedEvent() {}

    public WishlistAddedEvent(String productId, String userId) {
        this.productId = productId;
        this.userId = userId;
        this.timestamp = LocalDateTime.now();
    }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
