package com.ecommerce.shipping.application.service;

import com.ecommerce.shipping.application.ports.EventPublisher;
import com.ecommerce.shipping.domain.entity.Shipment;
import com.ecommerce.shipping.domain.repository.ShipmentRepository;
import com.ecommerce.shared.messaging.event.ShipmentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShippingApplicationService {

    private final ShipmentRepository shipmentRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public void handlePaymentCompleted(String orderId) {
        Shipment shipment = getOrCreateShipment(orderId);
        shipment.setPaymentCompleted(true);
        checkAndCreateShipment(shipment);
    }

    @Transactional
    public void handleStockReserved(String orderId) {
        Shipment shipment = getOrCreateShipment(orderId);
        shipment.setStockReserved(true);
        checkAndCreateShipment(shipment);
    }

    private Shipment getOrCreateShipment(String orderId) {
        return shipmentRepository.findByOrderId(orderId)
                .orElseGet(() -> Shipment.builder()
                        .id(UUID.randomUUID().toString())
                        .orderId(orderId)
                        .paymentCompleted(false)
                        .stockReserved(false)
                        .shipped(false)
                        .build());
    }

    private void checkAndCreateShipment(Shipment shipment) {
        if (shipment.isPaymentCompleted() && shipment.isStockReserved() && !shipment.isShipped()) {
            shipment.setShipped(true);
            shipmentRepository.save(shipment);
            eventPublisher.publish(new ShipmentCreatedEvent(UUID.randomUUID().toString(), shipment.getOrderId()));
        } else {
            shipmentRepository.save(shipment);
        }
    }
}
