package com.microservices.shipping.application.mapper;

import com.microservices.shipping.application.dto.ShipmentResponse;
import com.microservices.shipping.domain.model.Shipment;

import java.util.List;
import java.util.stream.Collectors;

public final class ShipmentMapper {
    
    private ShipmentMapper() {}
    
    public static ShipmentResponse toResponse(Shipment shipment) {
        if (shipment == null) return null;
        ShipmentResponse response = new ShipmentResponse();
        response.setShipmentId(shipment.getId());
        response.setOrderId(shipment.getOrderId());
        response.setTrackingNumber(shipment.getTrackingNumber());
        response.setStatus(shipment.getStatus().name());
        response.setCreatedAt(shipment.getCreatedAt());
        return response;
    }
    
    public static List<ShipmentResponse> toResponseList(List<Shipment> shipments) {
        return shipments.stream().map(ShipmentMapper::toResponse).collect(Collectors.toList());
    }
}
