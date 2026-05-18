package com.company.order.domain.event;

import com.company.shared.event.BaseEvent;
import com.company.order.domain.model.BeautyOrder.GiftWrapping;

public class BeautyOrderCreatedEvent extends BaseEvent {
    private String orderId;
    private String userId;
    private GiftWrapping giftWrapping;
    private boolean hasSamples;

    public BeautyOrderCreatedEvent() {
        super("BEAUTY_ORDER_CREATED", null, "order-service");
    }

    public BeautyOrderCreatedEvent(String orderId, String userId, GiftWrapping giftWrapping, boolean hasSamples) {
        this();
        this.orderId = orderId;
        this.userId = userId;
        this.giftWrapping = giftWrapping;
        this.hasSamples = hasSamples;
        setCorrelationId(orderId);
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public GiftWrapping getGiftWrapping() { return giftWrapping; }
    public void setGiftWrapping(GiftWrapping giftWrapping) { this.giftWrapping = giftWrapping; }
    public boolean isHasSamples() { return hasSamples; }
    public void setHasSamples(boolean hasSamples) { this.hasSamples = hasSamples; }
}
