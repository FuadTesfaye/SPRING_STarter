package com.example.paymentservice.infrastructure.config;

import com.example.paymentservice.application.usecases.ProcessPaymentService;
import com.example.paymentservice.application.usecases.ProcessPaymentUseCase;
import com.example.paymentservice.domain.interfaces.PaymentRepository;
import com.example.paymentservice.domain.services.PaymentDomainService;
import com.example.paymentservice.infrastructure.persistence.adapter.PaymentPersistenceAdapter;
import com.example.paymentservice.infrastructure.persistence.adapter.PaymentPersistenceMapper;
import com.example.paymentservice.infrastructure.persistence.repository.SpringDataPaymentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

    @Bean
    public PaymentDomainService paymentDomainService() {
        return new PaymentDomainService();
    }

    @Bean
    public PaymentPersistenceMapper paymentPersistenceMapper() {
        return new PaymentPersistenceMapper();
    }

    @Bean
    public PaymentRepository paymentRepository(
            SpringDataPaymentRepository springDataPaymentRepository,
            PaymentPersistenceMapper paymentPersistenceMapper
    ) {
        return new PaymentPersistenceAdapter(springDataPaymentRepository, paymentPersistenceMapper);
    }

    @Bean
    public ProcessPaymentService processPaymentUseCase(
            PaymentRepository paymentRepository,
            PaymentDomainService paymentDomainService
    ) {
        return new ProcessPaymentUseCase(paymentRepository, paymentDomainService);
    }
}
