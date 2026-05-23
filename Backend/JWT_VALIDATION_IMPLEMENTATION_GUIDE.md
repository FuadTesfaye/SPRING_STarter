# JWT Validation Implementation Guide for Non-Auth Microservices

This guide explains how to add JWT validation to Payment Service, Inventory Service, Order Service, Shipping Service, and Notification Service.

## Overview

Each microservice (except auth-service) needs to:
1. Have a JWT validator component
2. Have a JWT validation filter
3. Configure the filter in Security Configuration
4. Use the same JWT_SECRET as the auth service

---

## Implementation Steps

### Step 1: Copy and Adapt the JWT Validator

For each service, create a validator class based on the template:

**Location:** `{service}/src/main/java/com/ecommerce/{service}/infrastructure/security/{Service}JwtValidator.java`

**Template:** `PAYMENT_SERVICE_JWT_VALIDATOR_TEMPLATE.java`

Replace:
- `payment` with your service name (lowercase)
- `Payment` with your service name (capitalized)

### Step 2: Create the JWT Validation Filter

Create a new file for each service:

**Location:** `{service}/src/main/java/com/ecommerce/{service}/infrastructure/security/{Service}JwtValidationFilter.java`

```java
package com.ecommerce.payment.infrastructure.security;

import com.ecommerce.shared.security.SharedJwtValidationFilter;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class PaymentJwtValidationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(PaymentJwtValidationFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private PaymentJwtValidator jwtValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = extractJwtFromRequest(request);

            if (jwt != null && jwtValidator.validateToken(jwt)) {
                String username = jwtValidator.getUsernameFromToken(jwt);
                Long userId = jwtValidator.getUserIdFromToken(jwt);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                new ArrayList<>()
                        );

                request.setAttribute("userId", userId);
                request.setAttribute("username", username);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("JWT validated for user: {} (ID: {})", username, userId);
            }
        } catch (JwtException ex) {
            logger.debug("JWT validation failed: {}", ex.getMessage());
        } catch (Exception ex) {
            logger.debug("Error during JWT processing: {}", ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
```

### Step 3: Update Security Configuration

Modify `{service}/src/main/java/com/ecommerce/{service}/infrastructure/config/{Service}SecurityConfig.java`:

**Template:** `PAYMENT_SERVICE_SECURITY_CONFIG_TEMPLATE.java`

Add the JWT validation filter:

```java
package com.ecommerce.payment.infrastructure.config;

import com.ecommerce.payment.infrastructure.security.PaymentJwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class PaymentSecurityConfig {

    @Autowired
    private PaymentJwtValidationFilter jwtValidationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                .requestMatchers("/actuator/health").permitAll()
                .requestMatchers("/actuator/health/liveness").permitAll()
                .requestMatchers("/actuator/health/readiness").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

---

## Services Requiring Implementation

### 1. Payment Service (Port 8083)
- **Validator:** `PaymentJwtValidator`
- **Filter:** `PaymentJwtValidationFilter`
- **Config:** `PaymentSecurityConfig`

### 2. Inventory Service (Port 8084)
- **Validator:** `InventoryJwtValidator`
- **Filter:** `InventoryJwtValidationFilter`
- **Config:** `InventorySecurityConfig`

### 3. Order Service (Port 8082)
- **Validator:** `OrderJwtValidator`
- **Filter:** `OrderJwtValidationFilter`
- **Config:** `OrderSecurityConfig`

### 4. Shipping Service (Port 8085)
- **Validator:** `ShippingJwtValidator`
- **Filter:** `ShippingJwtValidationFilter`
- **Config:** `ShippingSecurityConfig`

### 5. Notification Service (Port 8086)
- **Validator:** `NotificationJwtValidator`
- **Filter:** `NotificationJwtValidationFilter`
- **Config:** `NotificationSecurityConfig`

---

## Example Usage in Controllers

```java
package com.ecommerce.payment.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    // Get userId from JWT stored in request
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(
            @PathVariable Long id,
            HttpServletRequest request) {
        
        Long userId = (Long) request.getAttribute("userId");
        String username = (String) request.getAttribute("username");
        
        // Use userId and username for authorization and logging
        // ...
    }

    // Using Spring Security Authentication
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestBody CreatePaymentRequest request,
            Authentication authentication) {
        
        String username = authentication.getName();
        // ...
    }
}
```

---

## RabbitMQ Event Listeners

Each service can listen to RabbitMQ events published by auth-service and other services.

### Example: Payment Service listening to Order Created Event

```java
package com.ecommerce.payment.infrastructure.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class PaymentEventListener {

    @RabbitListener(queues = "order.created")
    public void handleOrderCreated(String message) {
        // Parse event
        // Create payment record
        // Publish payment.processed event
    }
}
```

---

## Database Connection Verification

Verify each service connects to correct PostgreSQL schema:

```bash
# Connect to database
docker exec -it ecommerce-postgres psql -U postgres -d ecommerce

# List all schemas
\dn

# Check tables in a schema
\dt payment_schema.*
\dt inventory_schema.*
\dt order_schema.*
\dt shipping_schema.*
\dt notification_schema.*
```

---

## Testing JWT Validation

### 1. Get Access Token
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "email=test@example.com&password=TestPass123"
```

### 2. Call Protected Endpoint with Token
```bash
TOKEN="your_access_token_here"

curl -X GET http://localhost:8083/api/payments/1 \
  -H "Authorization: Bearer $TOKEN"
```

### 3. Verify Token Validation
```bash
# Invalid token should return 403 Forbidden
curl -X GET http://localhost:8083/api/payments/1 \
  -H "Authorization: Bearer invalid_token"

# No token should return 403 Forbidden
curl -X GET http://localhost:8083/api/payments/1
```

---

## Common Issues

### 1. JWT_SECRET Mismatch
**Error:** Token validation fails in non-auth services
**Solution:** Ensure JWT_SECRET environment variable is same in all services

### 2. Token Expiration
**Error:** "Token has expired"
**Solution:** Check JWT_EXPIRATION value, refresh token when expired

### 3. Invalid Bearer Token Format
**Error:** JWT not recognized
**Solution:** Ensure header format is exactly: `Authorization: Bearer <token>`

### 4. CORS Issues
**Error:** AJAX requests fail from frontend
**Solution:** Add CORS configuration to SecurityConfig

```java
@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
        }
    };
}
```

---

## Checklist

For each non-auth microservice:

- [ ] JWT validator class created
- [ ] JWT validation filter created
- [ ] Security configuration updated
- [ ] application.yml has correct database URL
- [ ] JWT_SECRET environment variable set
- [ ] Service can start without errors
- [ ] Actuator health endpoint accessible
- [ ] Protected endpoints require JWT token
- [ ] RabbitMQ listeners implemented
- [ ] Service-to-service communication includes JWT in headers

---

## References

- JWT Documentation: https://tools.ietf.org/html/rfc7519
- JJWT Library: https://github.com/jwtk/jjwt
- Spring Security: https://spring.io/projects/spring-security
- Docker Compose: Backend/docker-compose.yml
- Architecture Guide: Backend/ARCHITECTURE_GUIDE.md
