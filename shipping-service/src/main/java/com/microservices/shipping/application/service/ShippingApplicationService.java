package com.microservices.shipping.application.service;

import com.microservices.shipping.application.dto.ShipmentResponse;
import com.microservices.shipping.application.mapper.ShipmentMapper;
import com.microservices.shipping.domain.model.Shipment;
import com.microservices.shipping.domain.service.ShippingDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ShippingApplicationService {
    
    private static final Logger log = LoggerFactory.getLogger(ShippingApplicationService.class);
    private final ShippingDomainService domainService;
    
    public ShippingApplicationService(ShippingDomainService domainService) {
        this.domainService = domainService;
    }
    
    public ShipmentResponse getShipment(String id) {
        Shipment shipment = domainService.findById(id);
        return ShipmentMapper.toResponse(shipment);
    }
    
    public List<ShipmentResponse> getAllShipments() {
        List<Shipment> shipments = domainService.findAll();
        return ShipmentMapper.toResponseList(shipments);
    }
}
