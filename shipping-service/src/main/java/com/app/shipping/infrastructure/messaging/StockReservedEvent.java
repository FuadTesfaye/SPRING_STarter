package com.app.shipping.infrastructure.messaging;

import java.util.UUID;

public record StockReservedEvent(UUID orderId) {}
