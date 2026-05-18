package com.company.order.application.dto;

import com.company.order.domain.model.BeautyOrder.GiftWrapping;

public class GiftWrapRequest {
    private GiftWrapping giftWrapping;
    private String giftMessage;

    public GiftWrapping getGiftWrapping() { return giftWrapping; }
    public void setGiftWrapping(GiftWrapping giftWrapping) { this.giftWrapping = giftWrapping; }
    public String getGiftMessage() { return giftMessage; }
    public void setGiftMessage(String giftMessage) { this.giftMessage = giftMessage; }
}
