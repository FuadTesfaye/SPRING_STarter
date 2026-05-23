package com.ecom.shipping.application.dto;

import java.util.UUID;

public record ShipmentCreatedEvent(UUID shipmentId, UUID orderId, String trackingNumber) {}
