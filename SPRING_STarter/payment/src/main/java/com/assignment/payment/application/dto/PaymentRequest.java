package com.assignment.payment.application.dto;

public class PaymentRequest {
    private Long orderId;
    private Double amount;
    private String email;
    
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}