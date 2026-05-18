package com.company.product.application.dto;

public class ShadeResponse {
    private String code;
    private String name;
    private String hexColor;
    private boolean inStock;

    public ShadeResponse() {}

    public ShadeResponse(String code, String name, String hexColor, boolean inStock) {
        this.code = code;
        this.name = name;
        this.hexColor = hexColor;
        this.inStock = inStock;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getHexColor() { return hexColor; }
    public void setHexColor(String hexColor) { this.hexColor = hexColor; }
    public boolean isInStock() { return inStock; }
    public void setInStock(boolean inStock) { this.inStock = inStock; }
}
