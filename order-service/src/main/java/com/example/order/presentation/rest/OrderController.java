package com.example.order.presentation.rest;

import com.example.order.application.dto.OrderRequest;
import com.example.order.application.usecases.OrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderUseCase orderUseCase;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest request) {
        orderUseCase.createOrder(request);
        return ResponseEntity.ok("Order created and event published!");
    }
}