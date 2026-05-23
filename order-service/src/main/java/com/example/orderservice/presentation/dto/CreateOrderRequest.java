package com.example.orderservice.presentation.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public class CreateOrderRequest {
    @NotBlank public String productSku;
    @Min(1)   public int quantity;
    @NotNull  public BigDecimal amount;
}
