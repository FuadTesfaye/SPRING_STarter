package com.company.review.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product_reviews")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    private String productId;
    private String userId;
    private String userName;
    private String userSkinType;
    private String userSkinTone;
    private boolean verifiedPurchase;
    private String shadeSelected;
    
    private int overallRating;
    private int qualityRating;
    private int valueRating;
    
    private String title;
    
    @Column(length = 2000)
    private String body;
    
    @ElementCollection
    private List<String> pros;
    
    @ElementCollection
    private List<String> cons;
    
    @ElementCollection
    private List<String> tags;
    
    @ElementCollection
    private List<String> imageUrls;
    
    private boolean wouldRepurchase;
    private int helpfulVotes;
    
    private String status; // PENDING, APPROVED, REJECTED, FLAGGED
    private LocalDateTime createdAt;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserSkinType() { return userSkinType; }
    public void setUserSkinType(String userSkinType) { this.userSkinType = userSkinType; }
    public String getUserSkinTone() { return userSkinTone; }
    public void setUserSkinTone(String userSkinTone) { this.userSkinTone = userSkinTone; }
    public boolean isVerifiedPurchase() { return verifiedPurchase; }
    public void setVerifiedPurchase(boolean verifiedPurchase) { this.verifiedPurchase = verifiedPurchase; }
    public String getShadeSelected() { return shadeSelected; }
    public void setShadeSelected(String shadeSelected) { this.shadeSelected = shadeSelected; }
    public int getOverallRating() { return overallRating; }
    public void setOverallRating(int overallRating) { this.overallRating = overallRating; }
    public int getQualityRating() { return qualityRating; }
    public void setQualityRating(int qualityRating) { this.qualityRating = qualityRating; }
    public int getValueRating() { return valueRating; }
    public void setValueRating(int valueRating) { this.valueRating = valueRating; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public List<String> getPros() { return pros; }
    public void setPros(List<String> pros) { this.pros = pros; }
    public List<String> getCons() { return cons; }
    public void setCons(List<String> cons) { this.cons = cons; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }
    public boolean isWouldRepurchase() { return wouldRepurchase; }
    public void setWouldRepurchase(boolean wouldRepurchase) { this.wouldRepurchase = wouldRepurchase; }
    public int getHelpfulVotes() { return helpfulVotes; }
    public void setHelpfulVotes(int helpfulVotes) { this.helpfulVotes = helpfulVotes; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
