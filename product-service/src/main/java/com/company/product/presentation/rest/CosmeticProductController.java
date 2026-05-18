package com.company.product.presentation.rest;

import com.company.product.application.dto.CosmeticProductFilterRequest;
import com.company.product.application.dto.CosmeticProductResponse;
import com.company.product.application.dto.ShadeResponse;
import com.company.product.application.service.CosmeticProductService;
import com.company.product.infrastructure.messaging.ProductEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products/cosmetic")
public class CosmeticProductController {

    private final CosmeticProductService service;
    private final ProductEventPublisher publisher;

    public CosmeticProductController(CosmeticProductService service, ProductEventPublisher publisher) {
        this.service = service;
        this.publisher = publisher;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> filterProducts(@ModelAttribute CosmeticProductFilterRequest request) {
        List<CosmeticProductResponse> data = service.filterProducts(request);
        return ResponseEntity.ok(createResponse(data));
    }

    @GetMapping("/{id}/shades")
    public ResponseEntity<Map<String, Object>> getShades(@PathVariable String id, @RequestHeader(value="X-User-Id", required=false) String userId) {
        if (userId != null) {
            publisher.publishProductViewed(id, userId, "general");
        }
        return ResponseEntity.ok(createResponse(service.getProductShades(id)));
    }

    @GetMapping("/{id}/similar")
    public ResponseEntity<Map<String, Object>> getSimilarProducts(@PathVariable String id) {
        return ResponseEntity.ok(createResponse(service.getSimilarProducts(id)));
    }

    @GetMapping("/{id}/routine-pairs")
    public ResponseEntity<Map<String, Object>> getRoutinePairs(@PathVariable String id) {
        return ResponseEntity.ok(createResponse(service.getRoutinePairings(id)));
    }

    @GetMapping("/new-arrivals")
    public ResponseEntity<Map<String, Object>> getNewArrivals() {
        return ResponseEntity.ok(createResponse(service.getNewArrivals()));
    }

    @GetMapping("/best-sellers")
    public ResponseEntity<Map<String, Object>> getBestSellers() {
        return ResponseEntity.ok(createResponse(service.getBestSellers()));
    }

    @GetMapping("/brands")
    public ResponseEntity<Map<String, Object>> getAllBrands() {
        return ResponseEntity.ok(createResponse(service.getAllBrands()));
    }

    @PostMapping("/{id}/shade-select")
    public ResponseEntity<Void> selectShade(@PathVariable String id, @RequestParam String shadeCode, @RequestHeader("X-User-Id") String userId) {
        publisher.publishShadeSelected(id, shadeCode, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/wishlist")
    public ResponseEntity<Void> addToWishlist(@PathVariable String id, @RequestHeader("X-User-Id") String userId) {
        publisher.publishWishlistAdded(id, userId);
        return ResponseEntity.ok().build();
    }

    private <T> Map<String, Object> createResponse(T data) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("success", true);
        response.put("message", "Success");
        return response;
    }
}
