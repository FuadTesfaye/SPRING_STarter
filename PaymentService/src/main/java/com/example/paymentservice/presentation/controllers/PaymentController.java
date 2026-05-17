package com.example.paymentservice.presentation.controllers;

import com.example.paymentservice.application.dto.response.PaymentProcessResponse;
import com.example.paymentservice.application.usecases.ProcessPaymentService;
import com.example.paymentservice.presentation.controllers.request.PaymentRequest;
import com.example.paymentservice.presentation.mapper.PaymentPresentationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final ProcessPaymentService processPaymentUseCase;
    private final PaymentPresentationMapper paymentPresentationMapper;

    public PaymentController(ProcessPaymentService processPaymentUseCase) {
        this.processPaymentUseCase = processPaymentUseCase;
        this.paymentPresentationMapper = new PaymentPresentationMapper();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public PaymentProcessResponse process(@RequestBody PaymentRequest request) {
        return paymentPresentationMapper.toPresentation(
                processPaymentUseCase.execute(paymentPresentationMapper.toApplication(request))
        );
    }
}
