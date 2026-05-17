package com.example.shipmentservice.application.usecases;

import com.example.shipmentservice.application.dto.request.ShipmentCreateRequest;
import com.example.shipmentservice.application.dto.response.ShipmentResponse;
import com.example.shipmentservice.application.usecases.CreateShipmentService;
import com.example.shipmentservice.domain.entities.Shipment;
import com.example.shipmentservice.domain.interfaces.ShipmentRepository;
import com.example.shipmentservice.domain.services.ShipmentDomainService;

public class CreateShipmentUseCase implements CreateShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentDomainService shipmentDomainService;

    public CreateShipmentUseCase(ShipmentRepository shipmentRepository, ShipmentDomainService shipmentDomainService) {
        this.shipmentRepository = shipmentRepository;
        this.shipmentDomainService = shipmentDomainService;
    }

    public ShipmentResponse execute(ShipmentCreateRequest request) {
        validate(request);
        Shipment shipment = shipmentRepository.save(
                shipmentDomainService.createShipment(request.orderId(), request.productId(), request.quantity())
        );
        return new ShipmentResponse(
                shipment.shipmentId(),
                shipment.orderId(),
                shipment.status().name(),
                "Shipment created successfully"
        );
    }

    private void validate(ShipmentCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request body is required");
        }
        if (request.orderId() == null || request.orderId().isBlank()) {
            throw new IllegalArgumentException("orderId is required");
        }
        if (request.productId() == null) {
            throw new IllegalArgumentException("productId is required");
        }
        if (request.quantity() == null || request.quantity() <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
    }
}
