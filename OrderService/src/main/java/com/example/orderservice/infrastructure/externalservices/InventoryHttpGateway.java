package com.example.orderservice.infrastructure.externalservices;

import com.example.orderservice.application.dto.request.InventoryReserveRequest;
import com.example.orderservice.application.dto.response.InventoryReserveResponse;
import com.example.orderservice.application.handlers.ExternalServiceException;
import com.example.orderservice.application.interfaces.InventoryGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class InventoryHttpGateway implements InventoryGateway {

    private final WebClient webClient;

    public InventoryHttpGateway(WebClient.Builder webClientBuilder, @Value("${services.inventory.base-url}") String inventoryBaseUrl) {
        this.webClient = webClientBuilder.baseUrl(inventoryBaseUrl).build();
    }

    @Override
    public InventoryReserveResponse reserveInventory(InventoryReserveRequest request) {
        return post("/api/inventory/reserve", request, InventoryReserveResponse.class, "inventory-service");
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
