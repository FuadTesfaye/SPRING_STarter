package com.example.orderservice.presentation.controllers;

import com.example.orderservice.application.usecases.PlaceOrderService;
import com.example.orderservice.presentation.controllers.request.OrderRequest;
import com.example.orderservice.presentation.controllers.response.OrderResponseBody;
import com.example.orderservice.presentation.mapper.OrderPresentationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final PlaceOrderService placeOrderUseCase;
    private final OrderPresentationMapper orderPresentationMapper;

    public OrderController(PlaceOrderService placeOrderUseCase) {
        this.placeOrderUseCase = placeOrderUseCase;
        this.orderPresentationMapper = new OrderPresentationMapper();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseBody createOrder(@RequestBody OrderRequest request) {
        return orderPresentationMapper.toPresentation(
                placeOrderUseCase.execute(orderPresentationMapper.toApplication(request))
        );
    }
}
