package com.company.order.application.dto;

public class ReorderRequest {
    private String previousOrderId;

    public String getPreviousOrderId() { return previousOrderId; }
    public void setPreviousOrderId(String previousOrderId) { this.previousOrderId = previousOrderId; }
}
