package com.example.payment.application.service;

import com.example.payment.application.dto.OrderPaymentCommand;
import com.example.payment.domain.port.PaymentEventPublisherPort;
import com.example.payment.domain.service.PaymentPolicy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentForOrderApplicationService {

    private final PaymentPolicy paymentPolicy;
    private final PaymentEventPublisherPort paymentEvents;

    public void settlePaymentForOrder(OrderPaymentCommand cmd) {
        log.info("Processing payment for order {}", cmd.orderId());
        try {
            PaymentPolicy.Decision decision = paymentPolicy.evaluate(cmd.productId(), cmd.totalAmount());
            if (decision.outcome() == PaymentPolicy.Outcome.FAILED) {
                paymentEvents.publishFailed(cmd.orderId(), decision.failureReason());
                return;
            }
            paymentEvents.publishCompleted(cmd.orderId(), decision.amount());
        } catch (Exception ex) {
            log.error("Payment handling error for {}", cmd.orderId(), ex);
            paymentEvents.publishFailed(cmd.orderId(), ex.getMessage());
        }
    }
}
