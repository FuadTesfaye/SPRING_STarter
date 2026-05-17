package com.eventdriven.orderservice.presentation.controller;

import com.eventdriven.orderservice.application.dto.CreateOrderRequest;
import com.eventdriven.orderservice.application.dto.OrderResponse;
import com.eventdriven.orderservice.application.service.OrderApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderApplicationService orderApplicationService;
    
    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }
    
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderApplicationService.createOrder(request));
    }
}