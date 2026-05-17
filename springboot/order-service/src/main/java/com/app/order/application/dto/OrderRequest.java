package com.app.order.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderRequest(String customerId, BigDecimal amount) {}
