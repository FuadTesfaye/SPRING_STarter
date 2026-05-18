package com.company.order.domain.event;

import com.company.shared.event.BaseEvent;

public class GiftOrderCreatedEvent extends BaseEvent {
    private String orderId;
    private String giftMessage;

    public GiftOrderCreatedEvent() {
        super("GIFT_ORDER_CREATED", null, "order-service");
    }

    public GiftOrderCreatedEvent(String orderId, String giftMessage) {
        this();
        this.orderId = orderId;
        this.giftMessage = giftMessage;
        setCorrelationId(orderId);
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getGiftMessage() { return giftMessage; }
    public void setGiftMessage(String giftMessage) { this.giftMessage = giftMessage; }
}
