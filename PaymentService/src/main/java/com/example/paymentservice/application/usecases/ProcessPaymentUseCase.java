package com.example.paymentservice.application.usecases;

import com.example.paymentservice.application.dto.request.PaymentProcessRequest;
import com.example.paymentservice.application.dto.response.PaymentProcessResponse;
import com.example.paymentservice.application.usecases.ProcessPaymentService;
import com.example.paymentservice.domain.entities.Payment;
import com.example.paymentservice.domain.interfaces.PaymentRepository;
import com.example.paymentservice.domain.services.PaymentDomainService;

public class ProcessPaymentUseCase implements ProcessPaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentDomainService paymentDomainService;

    public ProcessPaymentUseCase(PaymentRepository paymentRepository, PaymentDomainService paymentDomainService) {
        this.paymentRepository = paymentRepository;
        this.paymentDomainService = paymentDomainService;
    }

    public PaymentProcessResponse execute(PaymentProcessRequest request) {
        validate(request);
        Payment payment = paymentRepository.save(
                paymentDomainService.createPayment(
                        request.orderId(),
                        request.productId(),
                        request.quantity(),
                        request.amount()
                )
        );
        return new PaymentProcessResponse(
                payment.paymentId(),
                payment.orderId(),
                payment.status().name(),
                "Payment processed successfully"
        );
    }

    private void validate(PaymentProcessRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request body is required");
        }
        if (request.orderId() == null || request.orderId().isBlank()) {
            throw new IllegalArgumentException("orderId is required");
        }
        if (request.productId() == null) {
            throw new IllegalArgumentException("productId is required");
        }
        if (request.quantity() == null || request.quantity() <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
        if (request.amount() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }
}
