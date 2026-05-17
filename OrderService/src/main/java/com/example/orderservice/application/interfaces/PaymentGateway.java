package com.example.orderservice.application.interfaces;

import com.example.orderservice.application.dto.request.PaymentProcessRequest;
import com.example.orderservice.application.dto.response.PaymentProcessResponse;

public interface PaymentGateway {

    PaymentProcessResponse processPayment(PaymentProcessRequest request);
}
