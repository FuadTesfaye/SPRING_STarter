package com.ecom.shipping.presentation.rest;

import com.ecom.shipping.domain.model.Shipment;
import com.ecom.shipping.domain.repository.ShipmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shipping")
@CrossOrigin(origins = "*")
public class ShippingController {
    private final ShipmentRepository shipmentRepository;

    public ShippingController(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @PostMapping
    public ResponseEntity<Shipment> createShipment(@RequestBody ShippingRequest request) {
        Shipment shipment = new Shipment(
                UUID.randomUUID(),
                request.getOrderId(),
                "SHIPPED",
                "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                LocalDateTime.now()
        );

        Shipment saved = shipmentRepository.save(shipment);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        return ResponseEntity.ok(shipmentRepository.findAll());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Shipment> getShipmentByOrderId(@PathVariable UUID orderId) {
        return shipmentRepository.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public static class ShippingRequest {
        private UUID orderId;

        public UUID getOrderId() { return orderId; }
        public void setOrderId(UUID orderId) { this.orderId = orderId; }
    }
}
