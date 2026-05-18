package com.company.order.presentation.rest;

import com.company.order.application.dto.BeautyOrderRequest;
import com.company.order.application.dto.BeautyOrderResponse;
import com.company.order.application.dto.GiftWrapRequest;
import com.company.order.application.service.BeautyOrderService;
import com.company.shared.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders/beauty")
public class BeautyOrderController {

    private final BeautyOrderService service;

    public BeautyOrderController(BeautyOrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BeautyOrderResponse>> createBeautyOrder(
            @RequestBody BeautyOrderRequest request,
            @RequestHeader("X-User-Id") String userId) {
        BeautyOrderResponse data = service.createBeautyOrder(request, userId);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PostMapping("/{orderId}/gift-wrap")
    public ResponseEntity<ApiResponse<BeautyOrderResponse>> addGiftWrapping(
            @PathVariable String orderId,
            @RequestBody GiftWrapRequest request) {
        BeautyOrderResponse data = service.addGiftWrapping(orderId, request);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @GetMapping("/user/{userId}/history-by-category")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getOrderHistoryByCategory(
            @PathVariable String userId) {
        Map<String, Object> data = service.getOrderHistoryByCategory(userId);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PostMapping("/{orderId}/reorder")
    public ResponseEntity<ApiResponse<Map<String, Object>>> reorder(
            @PathVariable String orderId,
            @RequestHeader("X-User-Id") String userId) {
        Map<String, Object> data = service.reorder(userId, orderId);
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}
