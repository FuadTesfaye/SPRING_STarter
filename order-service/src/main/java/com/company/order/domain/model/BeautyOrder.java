package com.company.order.domain.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "beauty_orders")
public class BeautyOrder {
    @Id
    @Column(name = "order_id")
    private String id;
    
    private String originalOrderId; // Reference to the base order

    @Enumerated(EnumType.STRING)
    private GiftWrapping giftWrapping;
    
    @Column(length = 500)
    private String giftMessage;
    
    private boolean isSample;
    private String beautyProfileId;

    @ElementCollection
    @CollectionTable(name = "beauty_order_shade_selections", joinColumns = @JoinColumn(name = "beauty_order_id"))
    private List<ShadeSelection> shadeSelections;

    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    public enum GiftWrapping {
        NONE, STANDARD, PREMIUM
    }

    public enum SubscriptionType {
        ONE_TIME, SUBSCRIBE_SAVE
    }

    @Embeddable
    public static class ShadeSelection {
        private String productId;
        private String shadeCode;

        public ShadeSelection() {}

        public ShadeSelection(String productId, String shadeCode) {
            this.productId = productId;
            this.shadeCode = shadeCode;
        }

        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        public String getShadeCode() { return shadeCode; }
        public void setShadeCode(String shadeCode) { this.shadeCode = shadeCode; }
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getOriginalOrderId() { return originalOrderId; }
    public void setOriginalOrderId(String originalOrderId) { this.originalOrderId = originalOrderId; }
    public GiftWrapping getGiftWrapping() { return giftWrapping; }
    public void setGiftWrapping(GiftWrapping giftWrapping) { this.giftWrapping = giftWrapping; }
    public String getGiftMessage() { return giftMessage; }
    public void setGiftMessage(String giftMessage) { this.giftMessage = giftMessage; }
    public boolean isSample() { return isSample; }
    public void setSample(boolean sample) { isSample = sample; }
    public String getBeautyProfileId() { return beautyProfileId; }
    public void setBeautyProfileId(String beautyProfileId) { this.beautyProfileId = beautyProfileId; }
    public List<ShadeSelection> getShadeSelections() { return shadeSelections; }
    public void setShadeSelections(List<ShadeSelection> shadeSelections) { this.shadeSelections = shadeSelections; }
    public SubscriptionType getSubscriptionType() { return subscriptionType; }
    public void setSubscriptionType(SubscriptionType subscriptionType) { this.subscriptionType = subscriptionType; }
}
