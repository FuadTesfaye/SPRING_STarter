package com.example.payment.presentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger Documentation for Payment Service
 * 
 * Access at: http://localhost:8083/swagger-ui.html
 * API Docs: http://localhost:8083/v3/api-docs
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Payment Service API",
                version = "1.0.0",
                description = "Payment processing service for the distributed event-driven system. " +
                        "Handles payment processing with mock payment gateway integration.",
                contact = @Contact(
                        name = "Payment Service Team",
                        email = "payment@example.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8083",
                        description = "Local Development Server"
                ),
                @Server(
                        url = "http://payment-service:8083",
                        description = "Docker Network"
                )
        }
)
public class PaymentApiDocumentation {
}
