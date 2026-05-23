package com.assignment.order.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateOrderRequest(
    @NotBlank String eventId,
    @Positive int quantity
) {}
