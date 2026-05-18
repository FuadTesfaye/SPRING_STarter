package com.app.payment.infrastructure.config;

import com.app.payment.application.ports.PaymentEventPublisher;
import com.app.payment.application.usecases.ProcessPaymentUseCase;
import com.app.payment.domain.PaymentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ProcessPaymentUseCase processPaymentUseCase(PaymentRepository paymentRepository, PaymentEventPublisher eventPublisher) {
        return new ProcessPaymentUseCase(paymentRepository, eventPublisher);
    }
}
