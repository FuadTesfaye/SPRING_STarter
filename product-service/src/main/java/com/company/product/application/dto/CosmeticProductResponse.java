package com.company.product.application.dto;

import java.util.List;

public class CosmeticProductResponse {
    private String id;
    private String productId;
    private String brand;
    private String brandTier;
    private String primaryCategory;
    private String subCategory;
    private String description;
    private List<String> shades;
    private List<String> skinTypes;
    private List<String> skinConcerns;
    private List<String> finishes;
    private List<String> keyIngredients;
    private boolean crueltyFree;
    private boolean vegan;
    private boolean cleanBeauty;
    private double averageRating;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getBrandTier() { return brandTier; }
    public void setBrandTier(String brandTier) { this.brandTier = brandTier; }
    public String getPrimaryCategory() { return primaryCategory; }
    public void setPrimaryCategory(String primaryCategory) { this.primaryCategory = primaryCategory; }
    public String getSubCategory() { return subCategory; }
    public void setSubCategory(String subCategory) { this.subCategory = subCategory; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<String> getShades() { return shades; }
    public void setShades(List<String> shades) { this.shades = shades; }
    public List<String> getSkinTypes() { return skinTypes; }
    public void setSkinTypes(List<String> skinTypes) { this.skinTypes = skinTypes; }
    public List<String> getSkinConcerns() { return skinConcerns; }
    public void setSkinConcerns(List<String> skinConcerns) { this.skinConcerns = skinConcerns; }
    public List<String> getFinishes() { return finishes; }
    public void setFinishes(List<String> finishes) { this.finishes = finishes; }
    public List<String> getKeyIngredients() { return keyIngredients; }
    public void setKeyIngredients(List<String> keyIngredients) { this.keyIngredients = keyIngredients; }
    public boolean isCrueltyFree() { return crueltyFree; }
    public void setCrueltyFree(boolean crueltyFree) { this.crueltyFree = crueltyFree; }
    public boolean isVegan() { return vegan; }
    public void setVegan(boolean vegan) { this.vegan = vegan; }
    public boolean isCleanBeauty() { return cleanBeauty; }
    public void setCleanBeauty(boolean cleanBeauty) { this.cleanBeauty = cleanBeauty; }
    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }
}
