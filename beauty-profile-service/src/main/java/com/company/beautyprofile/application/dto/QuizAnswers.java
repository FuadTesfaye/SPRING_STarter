package com.company.beautyprofile.application.dto;

import java.util.List;

public class QuizAnswers {
    private String skinType;
    private List<String> skinConcerns;
    private String skinTone;
    private String undertone;
    private String hairType;
    private List<String> hairConcerns;
    private List<String> avoidIngredients;
    private Boolean prefersCleanBeauty;
    private Boolean prefersVegan;
    private Boolean prefersCrueltyFree;
    private String budgetRange;

    // Getters and setters
    public String getSkinType() { return skinType; }
    public void setSkinType(String skinType) { this.skinType = skinType; }
    public List<String> getSkinConcerns() { return skinConcerns; }
    public void setSkinConcerns(List<String> skinConcerns) { this.skinConcerns = skinConcerns; }
    public String getSkinTone() { return skinTone; }
    public void setSkinTone(String skinTone) { this.skinTone = skinTone; }
    public String getUndertone() { return undertone; }
    public void setUndertone(String undertone) { this.undertone = undertone; }
    public String getHairType() { return hairType; }
    public void setHairType(String hairType) { this.hairType = hairType; }
    public List<String> getHairConcerns() { return hairConcerns; }
    public void setHairConcerns(List<String> hairConcerns) { this.hairConcerns = hairConcerns; }
    public List<String> getAvoidIngredients() { return avoidIngredients; }
    public void setAvoidIngredients(List<String> avoidIngredients) { this.avoidIngredients = avoidIngredients; }
    public Boolean getPrefersCleanBeauty() { return prefersCleanBeauty; }
    public void setPrefersCleanBeauty(Boolean prefersCleanBeauty) { this.prefersCleanBeauty = prefersCleanBeauty; }
    public Boolean getPrefersVegan() { return prefersVegan; }
    public void setPrefersVegan(Boolean prefersVegan) { this.prefersVegan = prefersVegan; }
    public Boolean getPrefersCrueltyFree() { return prefersCrueltyFree; }
    public void setPrefersCrueltyFree(Boolean prefersCrueltyFree) { this.prefersCrueltyFree = prefersCrueltyFree; }
    public String getBudgetRange() { return budgetRange; }
    public void setBudgetRange(String budgetRange) { this.budgetRange = budgetRange; }
}
