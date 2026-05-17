package com.example.paymentservice.presentation.mapper;

import com.example.paymentservice.application.dto.request.PaymentProcessRequest;
import com.example.paymentservice.application.dto.response.PaymentProcessResponse;
import com.example.paymentservice.presentation.controllers.request.PaymentRequest;

public class PaymentPresentationMapper {

    public PaymentProcessRequest toApplication(PaymentRequest request) {
        return new PaymentProcessRequest(request.orderId(), request.productId(), request.quantity(), request.amount());
    }

    public PaymentProcessResponse toPresentation(PaymentProcessResponse response) {
        return response;
    }
}
