package com.example.shippingservice.application.port;
import java.util.UUID;
public interface OrderReadinessPort {
    void markPaymentCompleted(UUID orderId);
    void markStockReserved(UUID orderId);
    boolean isReady(UUID orderId);
    void clear(UUID orderId);
}
