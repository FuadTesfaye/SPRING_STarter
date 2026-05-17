package com.example.orderservice.infrastructure.externalservices;

import com.example.orderservice.application.dto.request.SendNotificationRequest;
import com.example.orderservice.application.dto.response.NotificationResponse;
import com.example.orderservice.application.handlers.ExternalServiceException;
import com.example.orderservice.application.interfaces.NotificationGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class NotificationHttpGateway implements NotificationGateway {

    private final WebClient webClient;

    public NotificationHttpGateway(
            WebClient.Builder webClientBuilder,
            @Value("${services.notification.base-url}") String notificationBaseUrl
    ) {
        this.webClient = webClientBuilder.baseUrl(notificationBaseUrl).build();
    }

    @Override
    public NotificationResponse sendNotification(SendNotificationRequest request) {
        return post("/api/notifications", request, NotificationResponse.class, "notification-service");
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
