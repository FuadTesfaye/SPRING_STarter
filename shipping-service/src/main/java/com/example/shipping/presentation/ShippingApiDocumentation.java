package com.example.shipping.presentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger Documentation for Shipping Service
 * 
 * Access at: http://localhost:8085/swagger-ui.html
 * API Docs: http://localhost:8085/v3/api-docs
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Shipping Service API",
                version = "1.0.0",
                description = "Shipping management service for the distributed event-driven system. " +
                        "Handles shipment creation and tracking.",
                contact = @Contact(
                        name = "Shipping Service Team",
                        email = "shipping@example.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8085",
                        description = "Local Development Server"
                ),
                @Server(
                        url = "http://shipping-service:8085",
                        description = "Docker Network"
                )
        }
)
public class ShippingApiDocumentation {
}
