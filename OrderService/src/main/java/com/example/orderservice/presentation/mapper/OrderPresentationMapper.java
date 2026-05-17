package com.example.orderservice.presentation.mapper;

import com.example.orderservice.application.dto.request.OrderCreateRequest;
import com.example.orderservice.application.dto.response.OrderResponse;
import com.example.orderservice.presentation.controllers.request.OrderRequest;
import com.example.orderservice.presentation.controllers.response.OrderResponseBody;

public class OrderPresentationMapper {

    public OrderCreateRequest toApplication(OrderRequest request) {
        return new OrderCreateRequest(request.productId(), request.quantity());
    }

    public OrderResponseBody toPresentation(OrderResponse response) {
        return new OrderResponseBody(
                response.orderId(),
                response.productId(),
                response.quantity(),
                response.status(),
                response.inventoryStatus(),
                response.paymentStatus(),
                response.shipmentId(),
                response.shipmentStatus(),
                response.notificationStatus(),
                response.message()
        );
    }
}
