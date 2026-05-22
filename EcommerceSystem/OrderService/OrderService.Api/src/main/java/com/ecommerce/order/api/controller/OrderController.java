package com.ecommerce.order.api.controller;

import com.ecommerce.order.application.dto.OrderRequest;
import com.ecommerce.order.application.dto.OrderResponse;
import com.ecommerce.order.application.usecase.CreateOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        OrderResponse response = createOrderUseCase.createOrder(request);
        return ResponseEntity.ok(response);
    }
}
