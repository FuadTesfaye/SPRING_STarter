package com.example.shipmentservice.application.usecases;

import com.example.shipmentservice.application.dto.request.ShipmentCreateRequest;
import com.example.shipmentservice.application.dto.response.ShipmentResponse;

public interface CreateShipmentService {

    ShipmentResponse execute(ShipmentCreateRequest request);
}
