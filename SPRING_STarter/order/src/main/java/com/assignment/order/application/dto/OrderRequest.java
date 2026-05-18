package com.assignment.order.application.dto;

public class OrderRequest {
    private String productName;
    private Integer quantity;
    private Double price;
    private String email;
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}