package com.ecom.payment.application.port;

import java.math.BigDecimal;

public interface PaymentGateway {
    boolean process(BigDecimal amount);
}
