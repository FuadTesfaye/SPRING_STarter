package com.example.order.presentation.rest;

import com.example.order.application.service.OrderCreationService;
import com.example.order.domain.model.Order;
import com.example.order.presentation.dto.CreateOrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Presentation adapter — outer ring driving application use cases.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderCreationService orderCreationService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequestDto body) {
        try {
            Order order = orderCreationService.createOrder(
                    body.getUserId(),
                    body.getProductId(),
                    body.getQuantity(),
                    body.getUnitPrice()
            );
            return ResponseEntity.ok(Map.of(
                    "orderId", order.getOrderId(),
                    "status", order.getStatus(),
                    "totalPrice", order.getTotalPrice()
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}
