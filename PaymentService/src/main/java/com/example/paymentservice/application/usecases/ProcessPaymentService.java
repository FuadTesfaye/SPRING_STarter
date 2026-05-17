package com.example.paymentservice.application.usecases;

import com.example.paymentservice.application.dto.request.PaymentProcessRequest;
import com.example.paymentservice.application.dto.response.PaymentProcessResponse;

public interface ProcessPaymentService {

    PaymentProcessResponse execute(PaymentProcessRequest request);
}
