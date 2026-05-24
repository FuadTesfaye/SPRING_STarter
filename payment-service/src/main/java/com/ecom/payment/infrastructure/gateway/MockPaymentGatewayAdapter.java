package com.ecom.payment.infrastructure.gateway;

import com.ecom.payment.application.port.PaymentGateway;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class MockPaymentGatewayAdapter implements PaymentGateway {
    @Override
    public boolean process(BigDecimal amount) {
        // Mock logic: fail if amount > 5000
        return amount.compareTo(new BigDecimal("5000")) <= 0;
    }
}
