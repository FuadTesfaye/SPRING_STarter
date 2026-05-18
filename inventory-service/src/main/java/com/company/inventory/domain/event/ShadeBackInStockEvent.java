package com.company.inventory.domain.event;

import com.company.shared.event.BaseEvent;

public class ShadeBackInStockEvent extends BaseEvent {
    private String productId;
    private String shadeCode;
    private int availableQuantity;

    public ShadeBackInStockEvent() {
        super("SHADE_BACK_IN_STOCK", null, "inventory-service");
    }

    public ShadeBackInStockEvent(String productId, String shadeCode, int availableQuantity) {
        this();
        this.productId = productId;
        this.shadeCode = shadeCode;
        this.availableQuantity = availableQuantity;
    }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getShadeCode() { return shadeCode; }
    public void setShadeCode(String shadeCode) { this.shadeCode = shadeCode; }
    public int getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }
}
