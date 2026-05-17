package com.app.shipping.domain;
import java.util.UUID;
public record ShipmentCreated(UUID orderId, String trackingNumber) {}
