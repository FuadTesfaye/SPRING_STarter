package com.company.order.application.dto;

import com.company.order.domain.model.BeautyOrder.GiftWrapping;
import com.company.order.domain.model.BeautyOrder.SubscriptionType;
import com.company.order.domain.model.BeautyOrder.ShadeSelection;
import java.util.List;

public class BeautyOrderRequest {
    private String originalOrderId;
    private GiftWrapping giftWrapping;
    private String giftMessage;
    private boolean isSample;
    private String beautyProfileId;
    private List<ShadeSelection> shadeSelections;
    private SubscriptionType subscriptionType;

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
