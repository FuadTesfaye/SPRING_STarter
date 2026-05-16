package com.ecommerce.order.presentation.controller;

import com.ecommerce.order.application.service.OrderService;
import com.ecommerce.order.domain.model.Order;
import com.ecommerce.order.presentation.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order management endpoints")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create a new order", description = "Creates order and publishes order.created event")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest req) {
        Order order = orderService.createOrder(
                req.getUserId(), req.getProductId(),
                req.getQuantity(), req.getAmount(), req.getShippingAddress());
        return ResponseEntity.ok(OrderResponse.from(order));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String id) {
        return orderService.findById(id)
                .map(o -> ResponseEntity.ok(OrderResponse.from(o)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.findAll().stream()
                .map(OrderResponse::from).toList();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get orders by user ID")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable String userId) {
        List<OrderResponse> orders = orderService.findByUserId(userId).stream()
                .map(OrderResponse::from).toList();
        return ResponseEntity.ok(orders);
    }
}
