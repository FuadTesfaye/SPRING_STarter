package com.example.orderservice.application.interfaces;

import com.example.orderservice.application.dto.request.ShipmentCreateRequest;
import com.example.orderservice.application.dto.response.ShipmentResponse;

public interface ShipmentGateway {

    ShipmentResponse createShipment(ShipmentCreateRequest request);
}
