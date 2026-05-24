package com.ecom.shipping.infrastructure.messaging;

import com.ecom.shipping.application.dto.PaymentCompletedEvent;
import com.ecom.shipping.application.service.ShipmentCoordinator;

public class PaymentCompletedListener {
    private final ShipmentCoordinator coordinator;

    public PaymentCompletedListener(ShipmentCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        coordinator.handlePaymentCompleted(event.orderId());
    }
}
