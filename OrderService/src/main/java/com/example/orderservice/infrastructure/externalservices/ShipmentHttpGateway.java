package com.example.orderservice.infrastructure.externalservices;

import com.example.orderservice.application.dto.request.ShipmentCreateRequest;
import com.example.orderservice.application.dto.response.ShipmentResponse;
import com.example.orderservice.application.handlers.ExternalServiceException;
import com.example.orderservice.application.interfaces.ShipmentGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class ShipmentHttpGateway implements ShipmentGateway {

    private final WebClient webClient;

    public ShipmentHttpGateway(WebClient.Builder webClientBuilder, @Value("${services.shipping.base-url}") String shipmentBaseUrl) {
        this.webClient = webClientBuilder.baseUrl(shipmentBaseUrl).build();
    }

    @Override
    public ShipmentResponse createShipment(ShipmentCreateRequest request) {
        return post("/api/shipping", request, ShipmentResponse.class, "shipping-service");
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
