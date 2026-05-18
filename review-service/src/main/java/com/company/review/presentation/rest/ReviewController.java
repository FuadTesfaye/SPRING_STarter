package com.company.review.presentation.rest;

import com.company.review.application.service.ReviewService;
import com.company.review.domain.model.ProductReview;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> submitReview(@RequestBody ProductReview review) {
        return ResponseEntity.ok(wrap(service.submitReview(review)));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Map<String, Object>> getProductReviews(
            @PathVariable String productId,
            @ModelAttribute Object filters) {
        return ResponseEntity.ok(wrap(service.getProductReviews(productId, filters)));
    }

    @GetMapping("/product/{productId}/summary")
    public ResponseEntity<Map<String, Object>> getReviewSummary(@PathVariable String productId) {
        return ResponseEntity.ok(wrap(service.getReviewSummary(productId)));
    }

    @PostMapping("/{reviewId}/helpful")
    public ResponseEntity<Map<String, Object>> voteHelpful(
            @PathVariable String reviewId,
            @RequestHeader("X-User-Id") String userId) {
        service.voteHelpful(reviewId, userId);
        return ResponseEntity.ok(wrap(null));
    }

    @PostMapping("/{reviewId}/report")
    public ResponseEntity<Map<String, Object>> reportReview(
            @PathVariable String reviewId,
            @RequestHeader("X-User-Id") String userId,
            @RequestBody Map<String, String> body) {
        service.reportReview(reviewId, userId, body.get("reason"));
        return ResponseEntity.ok(wrap(null));
    }

    private <T> Map<String, Object> wrap(T data) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("success", true);
        return map;
    }
}
