package com.ecommerce.shipping.application.service;

import com.ecommerce.shipping.application.port.EventPublisher;
import com.ecommerce.shipping.domain.event.*;
import com.ecommerce.shipping.domain.model.*;
import com.ecommerce.shipping.domain.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentSagaRepository sagaRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("[SHIPPING] Payment completed for order: {}", event.getOrderId());

        ShipmentSaga saga = sagaRepository.findByOrderId(event.getOrderId())
                .orElse(ShipmentSaga.builder()
                        .orderId(event.getOrderId())
                        .status(ShipmentSaga.SagaStatus.WAITING)
                        .paymentCompleted(false)
                        .stockReserved(false)
                        .updatedAt(LocalDateTime.now())
                        .build());

        saga.markPaymentCompleted();
        sagaRepository.save(saga);

        if (saga.isReadyToShip()) {
            createShipment(saga);
        } else {
            log.info("[SHIPPING] Waiting for stock reservation for order: {}", event.getOrderId());
        }
    }

    @Transactional
    public void handleStockReserved(StockReservedEvent event) {
        log.info("[SHIPPING] Stock reserved for order: {}", event.getOrderId());

        ShipmentSaga saga = sagaRepository.findByOrderId(event.getOrderId())
                .orElse(ShipmentSaga.builder()
                        .orderId(event.getOrderId())
                        .status(ShipmentSaga.SagaStatus.WAITING)
                        .paymentCompleted(false)
                        .stockReserved(false)
                        .updatedAt(LocalDateTime.now())
                        .build());

        // Use a placeholder address since we don't have it in this event.
        // In a real system, we'd get this from the order event or pass it along.
        saga.markStockReserved("Address from order");
        sagaRepository.save(saga);

        if (saga.isReadyToShip()) {
            createShipment(saga);
        } else {
            log.info("[SHIPPING] Waiting for payment confirmation for order: {}", event.getOrderId());
        }
    }

    private void createShipment(ShipmentSaga saga) {
        // Avoid duplicate shipments
        if (shipmentRepository.findByOrderId(saga.getOrderId()).isPresent()) {
            log.warn("[SHIPPING] Shipment already exists for order: {}", saga.getOrderId());
            return;
        }

        Shipment shipment = Shipment.builder()
                .orderId(saga.getOrderId())
                .shippingAddress(saga.getShippingAddress())
                .status(ShipmentStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();
        shipment.generateTrackingNumber();

        Shipment saved = shipmentRepository.save(shipment);
        saga.setStatus(ShipmentSaga.SagaStatus.COMPLETED);
        sagaRepository.save(saga);

        log.info("[SHIPPING] Shipment created: {} for order: {}", saved.getId(), saga.getOrderId());

        eventPublisher.publish("shipment.created", ShipmentCreatedEvent.builder()
                .shipmentId(saved.getId())
                .orderId(saved.getOrderId())
                .shippingAddress(saved.getShippingAddress())
                .trackingNumber(saved.getTrackingNumber())
                .timestamp(LocalDateTime.now())
                .build());
    }

    public List<Shipment> findAll() { return shipmentRepository.findAll(); }
    public java.util.Optional<Shipment> findByOrderId(String orderId) { return shipmentRepository.findByOrderId(orderId); }
}
