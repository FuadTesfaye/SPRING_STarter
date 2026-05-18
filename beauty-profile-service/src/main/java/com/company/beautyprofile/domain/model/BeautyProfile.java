package com.company.beautyprofile.domain.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "beauty_profiles")
public class BeautyProfile {
    @Id
    @Column(name = "user_id")
    private String userId;

    private String skinType;
    
    @ElementCollection
    private List<String> skinConcerns;
    
    private String skinTone;
    private String undertone;
    private String hairType;
    
    @ElementCollection
    private List<String> hairConcerns;
    
    @ElementCollection
    private List<String> preferredBrands;
    
    @ElementCollection
    private List<String> avoidIngredients;
    
    @ElementCollection
    private List<String> preferredFinishes;
    
    private boolean prefersCleanBeauty;
    private boolean prefersVegan;
    private boolean prefersCrueltyFree;
    private String budgetRange;
    private String completionStatus; // INCOMPLETE, PARTIAL, COMPLETE

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
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
    public List<String> getPreferredBrands() { return preferredBrands; }
    public void setPreferredBrands(List<String> preferredBrands) { this.preferredBrands = preferredBrands; }
    public List<String> getAvoidIngredients() { return avoidIngredients; }
    public void setAvoidIngredients(List<String> avoidIngredients) { this.avoidIngredients = avoidIngredients; }
    public List<String> getPreferredFinishes() { return preferredFinishes; }
    public void setPreferredFinishes(List<String> preferredFinishes) { this.preferredFinishes = preferredFinishes; }
    public boolean isPrefersCleanBeauty() { return prefersCleanBeauty; }
    public void setPrefersCleanBeauty(boolean prefersCleanBeauty) { this.prefersCleanBeauty = prefersCleanBeauty; }
    public boolean isPrefersVegan() { return prefersVegan; }
    public void setPrefersVegan(boolean prefersVegan) { this.prefersVegan = prefersVegan; }
    public boolean isPrefersCrueltyFree() { return prefersCrueltyFree; }
    public void setPrefersCrueltyFree(boolean prefersCrueltyFree) { this.prefersCrueltyFree = prefersCrueltyFree; }
    public String getBudgetRange() { return budgetRange; }
    public void setBudgetRange(String budgetRange) { this.budgetRange = budgetRange; }
    public String getCompletionStatus() { return completionStatus; }
    public void setCompletionStatus(String completionStatus) { this.completionStatus = completionStatus; }
}
