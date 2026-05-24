package com.ecom.payment.application.port;

import com.ecom.payment.application.dto.PaymentProcessedEvent;

public interface EventPublisher {
    void publishPaymentProcessed(PaymentProcessedEvent event);
}
