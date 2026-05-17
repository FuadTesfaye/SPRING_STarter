package com.example.orderservice.application.usecases;

import com.example.orderservice.application.dto.request.OrderCreateRequest;
import com.example.orderservice.application.dto.response.OrderResponse;

public interface PlaceOrderService {

    OrderResponse execute(OrderCreateRequest request);
}
