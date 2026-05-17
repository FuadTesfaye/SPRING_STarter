package com.example.shipmentservice.presentation.mapper;

import com.example.shipmentservice.application.dto.request.ShipmentCreateRequest;
import com.example.shipmentservice.application.dto.response.ShipmentResponse;
import com.example.shipmentservice.presentation.controllers.request.ShipmentRequest;

public class ShipmentPresentationMapper {

    public ShipmentCreateRequest toApplication(ShipmentRequest request) {
        return new ShipmentCreateRequest(request.orderId(), request.productId(), request.quantity());
    }

    public ShipmentResponse toPresentation(ShipmentResponse response) {
        return response;
    }
}
