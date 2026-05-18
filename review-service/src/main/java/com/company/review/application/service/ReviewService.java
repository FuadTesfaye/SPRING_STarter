package com.company.review.application.service;

import com.company.review.domain.model.ProductReview;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final RabbitTemplate rabbitTemplate;

    public ReviewService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public ProductReview submitReview(ProductReview review) {
        review.setStatus("PENDING");
        review.setCreatedAt(LocalDateTime.now());
        // Save
        rabbitTemplate.convertAndSend("beauty.events", "review.submitted", review.getProductId());
        return review;
    }

    public List<ProductReview> getProductReviews(String productId, Object filters) {
        return new ArrayList<>();
    }

    public Object getReviewSummary(String productId) {
        return new Object();
    }

    public void voteHelpful(String reviewId, String userId) {
        // Increment helpful votes
    }

    public void reportReview(String reviewId, String userId, String reason) {
        // Log report
    }

    public List<ProductReview> getPhotoReviews(String productId) {
        return new ArrayList<>();
    }

    public List<ProductReview> getUserReviews(String userId) {
        return new ArrayList<>();
    }
}
