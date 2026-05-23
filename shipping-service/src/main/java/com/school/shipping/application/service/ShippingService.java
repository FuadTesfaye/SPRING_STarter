package com.school.shipping.application.service;

import com.school.shipping.application.port.EventPublisher;
import com.school.shipping.application.port.ShipmentRepository;
import com.school.shipping.domain.entity.Shipment;
import com.school.shipping.domain.event.ShipmentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingService {

    private final ShipmentRepository shipmentRepository;
    private final EventPublisher eventPublisher;

    public void createShipment(Long orderId, String studentId) {
        log.info("[SHIPPING] Creating shipment for order: {}", orderId);

        Shipment shipment = new Shipment(orderId, studentId);
        shipment.setTrackingNumber("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        shipment.setStatus("SHIPPED");

        Shipment saved = shipmentRepository.save(shipment);

        ShipmentCreatedEvent event = new ShipmentCreatedEvent(
            orderId, saved.getId(), studentId, saved.getTrackingNumber()
        );
        eventPublisher.publish("shipment.created", event);
        log.info("[SHIPPING] Shipment created: {} tracking: {}", saved.getId(), saved.getTrackingNumber());
    }
}
