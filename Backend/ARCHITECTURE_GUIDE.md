# E-Commerce Microservices Architecture - Implementation Guide

## Architecture Overview

This refactored architecture uses:

### Authentication & Authorization
- **Supabase Auth** for:
  - User signup/login
  - Email verification
  - Password reset
  - JWT token generation
  - Auth-related email sending

- **Local PostgreSQL** for:
  - Payment Service (payment_schema)
  - Inventory Service (inventory_schema)
  - Order Service (order_schema)
  - Shipping Service (shipping_schema)
  - Notification Service (notification_schema)

### Event-Driven Communication
- **RabbitMQ** for inter-service communication
- Topic exchange: `app.exchange`
- Event-based communication patterns

### Database
- **PostgreSQL 16** (Docker container)
- Separate schemas for each microservice
- Initialized via `sql/init-schemas.sql`

---

## Service Configuration

### 1. Auth Service (Port 8081)
- Uses Supabase REST/Auth API
- **Does NOT** connect to local PostgreSQL
- JWT token generation and validation
- Environment variables required:
  ```
  SUPABASE_URL=https://your-project.supabase.co
  SUPABASE_ANON_KEY=your-anon-key
  SUPABASE_SERVICE_ROLE_KEY=your-service-role-key
  JWT_SECRET=your-jwt-secret
  JWT_EXPIRATION=86400000
  ```

### 2. Other Services (Payment, Inventory, Order, Shipping, Notification)
- All connect to local PostgreSQL at `postgres:5432/ecommerce`
- Use environment variables for database connection:
  ```
  SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/ecommerce
  SPRING_DATASOURCE_USERNAME=postgres
  SPRING_DATASOURCE_PASSWORD=postgres
  ```
- Use shared JWT validation filter for auth
- Listen to RabbitMQ events from auth-service

---

## Docker Compose Configuration

### Service Dependencies
```
postgres:5432 (healthy)
    └── rabbitmq:5672 (healthy)
        ├── auth-service:8081
        ├── payment-service:8083
        ├── inventory-service:8084
        ├── order-service:8082
        ├── shipping-service:8085
        └── notification-service:8086
```

**Wait conditions:**
- `auth-service`: Waits for rabbitmq (no postgres dependency)
- All other services: Wait for both postgres and rabbitmq

---

## API Endpoints

### Auth Service - `/api/auth`

#### 1. Sign Up
```
POST /api/auth/signup
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securePassword123",
  "confirmPassword": "securePassword123",
  "firstName": "John",
  "lastName": "Doe"
}

Response (201):
{
  "success": true,
  "userId": "user_id_123",
  "email": "user@example.com",
  "message": "User registered. Please verify your email."
}
```

#### 2. Login
```
POST /api/auth/login?email=user@example.com&password=securePassword123

Response (200):
{
  "success": true,
  "accessToken": "eyJhbGc...",
  "refreshToken": "refresh_token_123",
  "expiresIn": 3600
}
```

#### 3. Verify Email
```
POST /api/auth/verify-email?token=email_verification_token

Response (200):
{
  "success": true,
  "message": "Email verified successfully"
}
```

#### 4. Forgot Password
```
POST /api/auth/forgot-password?email=user@example.com

Response (200):
{
  "success": true,
  "message": "If this email is registered, you will receive a password reset link"
}
```

#### 5. Reset Password
```
POST /api/auth/reset-password?token=reset_token&newPassword=newPass123&confirmPassword=newPass123

Response (200):
{
  "success": true,
  "message": "Password reset successfully. Please log in with your new password."
}
```

#### 6. Refresh Token
```
POST /api/auth/refresh-token?refreshToken=refresh_token_123

Response (200):
{
  "success": true,
  "accessToken": "new_eyJhbGc...",
  "refreshToken": "refresh_token_123",
  "expiresIn": 3600
}
```

---

## JWT Token Format

```json
Header:
{
  "alg": "HS512",
  "typ": "JWT"
}

Payload:
{
  "sub": "username",
  "userId": 123,
  "iat": 1234567890,
  "exp": 1234654290
}

Signature:
HMACSHA512(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)
```

### Token Usage
```
Authorization: Bearer <access_token>
```

---

## Inter-Service Communication

### Using JWT in Service-to-Service Calls

When calling other services, include the JWT token:

```java
@Service
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;
    
    public void callPaymentService(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        restTemplate.exchange(
            "http://payment-service:8083/api/payments",
            HttpMethod.GET,
            entity,
            PaymentResponse.class
        );
    }
}
```

### JWT Validation Filter for Non-Auth Services

Each service should implement JWT validation. Example for Payment Service:

```java
package com.ecommerce.payment.infrastructure.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.ecommerce.shared.security.SharedJwtValidationFilter;

import javax.crypto.SecretKey;

@Component
public class PaymentJwtValidator implements SharedJwtValidationFilter.JwtTokenValidator {

    @Value("${jwt.secret:mySecretKeyForEcommerceAuth2024}")
    private String jwtSecret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    @Override
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("userId", Long.class);
    }
}
```

---

## RabbitMQ Events

### Published Events from Auth Service

#### User Registered Event
```
Exchange: app.exchange
Routing Key: auth.user.registered
Queue: auth.user.registered

Payload:
{
  "userId": 123,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "timestamp": 1234567890
}
```

#### User Email Verified Event
```
Exchange: app.exchange
Routing Key: auth.user.verified
Queue: auth.user.verified

Payload:
{
  "userId": 123,
  "email": "user@example.com",
  "timestamp": 1234567890
}
```

### Events from Other Services

See `docker-compose.yml` RabbitMQ configuration for complete event structure.

---

## Startup Procedure

### Prerequisites
1. Docker and Docker Compose installed
2. Supabase account with project created
3. Environment variables configured in `.env`

### Start Services
```bash
# From Backend directory
docker-compose up -d

# Check status
docker-compose ps

# View logs
docker-compose logs -f auth-service
docker-compose logs -f payment-service
# etc.

# Stop all services
docker-compose down
```

### Verify PostgreSQL Connection
```bash
docker exec -it ecommerce-postgres psql -U postgres -d ecommerce -c "
  SELECT schema_name FROM information_schema.schemata 
  WHERE schema_name LIKE '%_schema' 
  ORDER BY schema_name;"
```

### Test Auth Service
```bash
curl -X POST http://localhost:8081/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "SecurePass123",
    "confirmPassword": "SecurePass123",
    "firstName": "Test",
    "lastName": "User"
  }'
```

---

## Health Checks

### Service Health Endpoints
All services expose health endpoints at:
```
http://<service-host>:<port>/actuator/health
```

Example:
- Auth Service: http://localhost:8081/actuator/health
- Payment Service: http://localhost:8083/actuator/health
- etc.

---

## Configuration Files Location

- **Docker Compose**: `Backend/docker-compose.yml`
- **Environment Variables**: `Backend/.env`
- **Auth Service Config**: `Backend/auth-service/src/main/resources/application.yml`
- **Payment Service Config**: `Backend/payment-service/src/main/resources/application.yml`
- **Database Init Script**: `Backend/sql/init-schemas.sql`
- **JWT Filter**: `Backend/auth-service/src/main/java/com/ecommerce/auth/infrastructure/security/JwtValidationFilter.java`

---

## Environment Variables Template

Copy to `.env` file:

```env
# ==================== Supabase Configuration ====================
SUPABASE_URL=https://your-project.supabase.co
SUPABASE_ANON_KEY=eyJhbGci...
SUPABASE_SERVICE_ROLE_KEY=eyJhbGci...

# ==================== Local PostgreSQL Configuration ====================
POSTGRES_HOST=postgres
POSTGRES_PORT=5432
POSTGRES_DB=ecommerce
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres

# ==================== JWT Configuration ====================
JWT_SECRET=your-secret-key-change-in-production
JWT_EXPIRATION=86400000

# ==================== RabbitMQ Configuration ====================
SPRING_RABBITMQ_HOST=rabbitmq
SPRING_RABBITMQ_PORT=5672
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest

# ==================== Email Configuration ====================
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password
MAIL_FROM=noreply@ecommerce.com

# ==================== Service URLs ====================
AUTH_SERVICE_URL=http://auth-service:8081
PAYMENT_SERVICE_URL=http://payment-service:8083
INVENTORY_SERVICE_URL=http://inventory-service:8084
ORDER_SERVICE_URL=http://order-service:8082
SHIPPING_SERVICE_URL=http://shipping-service:8085
NOTIFICATION_SERVICE_URL=http://notification-service:8086
```

---

## Troubleshooting

### Services Won't Start
1. Check Docker logs: `docker-compose logs service-name`
2. Verify database is healthy: `docker exec ecommerce-postgres pg_isready`
3. Verify RabbitMQ is healthy: `docker exec ecommerce-rabbitmq rabbitmq-diagnostics ping`

### JWT Validation Errors
1. Ensure JWT_SECRET matches across all services
2. Check token expiration: `JWT_EXPIRATION`
3. Verify Authorization header format: `Bearer <token>`

### Database Connection Issues
1. Check connection string in application.yml
2. Verify database credentials in docker-compose.yml
3. Ensure postgres service is healthy

### RabbitMQ Connection Issues
1. Check SPRING_RABBITMQ_HOST environment variable
2. Verify RabbitMQ service is running
3. Check connection timeout settings

---

## Production Deployment

### Security Best Practices

1. **Change default credentials**
   - PostgreSQL: Change `postgres` password
   - RabbitMQ: Change `guest` credentials
   - JWT: Use strong secret key

2. **Environment Variables**
   - Never commit `.env` file
   - Use secret management (HashiCorp Vault, AWS Secrets Manager, etc.)

3. **SSL/TLS**
   - Enable HTTPS for all services
   - Use valid certificates

4. **Database**
   - Use managed PostgreSQL service (AWS RDS, Supabase, etc.)
   - Enable backups and replication
   - Use connection pooling

5. **RabbitMQ**
   - Use managed message broker service
   - Enable persistent storage
   - Configure cluster mode

---

## Next Steps

1. Update auth service pom.xml with Supabase SDK dependencies
2. Implement JWT validators in each microservice
3. Add security filters to non-auth services
4. Deploy and test end-to-end flows
5. Configure production monitoring and logging
