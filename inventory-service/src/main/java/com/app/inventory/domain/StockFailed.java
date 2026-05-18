package com.app.inventory.domain;

import java.util.UUID;

public record StockFailed(UUID orderId, String reason) {}
