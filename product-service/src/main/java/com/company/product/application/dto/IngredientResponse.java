package com.company.product.application.dto;

public class IngredientResponse {
    private String name;
    private String benefit;

    public IngredientResponse() {}

    public IngredientResponse(String name, String benefit) {
        this.name = name;
        this.benefit = benefit;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBenefit() { return benefit; }
    public void setBenefit(String benefit) { this.benefit = benefit; }
}
