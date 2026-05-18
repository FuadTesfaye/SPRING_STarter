package com.company.loyalty.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "loyalty_accounts")
public class LoyaltyAccount {
    @Id
    @Column(name = "user_id")
    private String userId;
    
    private String tier; // BRONZE, SILVER, GOLD, PLATINUM, DIAMOND
    private int currentPoints;
    private int lifetimePoints;
    private double totalSpend;
    private String memberSince;

    // Getters and setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getTier() { return tier; }
    public void setTier(String tier) { this.tier = tier; }
    public int getCurrentPoints() { return currentPoints; }
    public void setCurrentPoints(int currentPoints) { this.currentPoints = currentPoints; }
    public int getLifetimePoints() { return lifetimePoints; }
    public void setLifetimePoints(int lifetimePoints) { this.lifetimePoints = lifetimePoints; }
    public double getTotalSpend() { return totalSpend; }
    public void setTotalSpend(double totalSpend) { this.totalSpend = totalSpend; }
    public String getMemberSince() { return memberSince; }
    public void setMemberSince(String memberSince) { this.memberSince = memberSince; }
}
