package com.example.orderservice.infrastructure.externalservices;

import com.example.orderservice.application.dto.request.PaymentProcessRequest;
import com.example.orderservice.application.dto.response.PaymentProcessResponse;
import com.example.orderservice.application.handlers.ExternalServiceException;
import com.example.orderservice.application.interfaces.PaymentGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class PaymentHttpGateway implements PaymentGateway {

    private final WebClient webClient;

    public PaymentHttpGateway(WebClient.Builder webClientBuilder, @Value("${services.payment.base-url}") String paymentBaseUrl) {
        this.webClient = webClientBuilder.baseUrl(paymentBaseUrl).build();
    }

    @Override
    public PaymentProcessResponse processPayment(PaymentProcessRequest request) {
        return post("/api/payments", request, PaymentProcessResponse.class, "payment-service");
    }

    private <T> T post(String path, Object request, Class<T> responseType, String serviceName) {
        try {
            return webClient.post()
                    .uri(path)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();
        } catch (WebClientResponseException ex) {
            throw new ExternalServiceException(serviceName + " returned an invalid response: " + ex.getStatusCode(), ex);
        } catch (WebClientException ex) {
            throw new ExternalServiceException(serviceName + " call failed: " + ex.getMessage(), ex);
        }
    }
}
