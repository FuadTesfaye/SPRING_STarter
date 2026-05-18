package com.company.product.application.dto;

import java.util.List;

public class CosmeticProductFilterRequest {
    private String category;
    private List<String> brand;
    private List<String> skinType;
    private List<String> skinConcern;
    private List<String> finish;
    private Boolean isCrueltyFree;
    private Boolean isVegan;
    private Boolean isCleanBeauty;

    // Getters and setters
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public List<String> getBrand() { return brand; }
    public void setBrand(List<String> brand) { this.brand = brand; }
    public List<String> getSkinType() { return skinType; }
    public void setSkinType(List<String> skinType) { this.skinType = skinType; }
    public List<String> getSkinConcern() { return skinConcern; }
    public void setSkinConcern(List<String> skinConcern) { this.skinConcern = skinConcern; }
    public List<String> getFinish() { return finish; }
    public void setFinish(List<String> finish) { this.finish = finish; }
    public Boolean getIsCrueltyFree() { return isCrueltyFree; }
    public void setIsCrueltyFree(Boolean isCrueltyFree) { this.isCrueltyFree = isCrueltyFree; }
    public Boolean getIsVegan() { return isVegan; }
    public void setIsVegan(Boolean isVegan) { this.isVegan = isVegan; }
    public Boolean getIsCleanBeauty() { return isCleanBeauty; }
    public void setIsCleanBeauty(Boolean isCleanBeauty) { this.isCleanBeauty = isCleanBeauty; }
}
