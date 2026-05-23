# Quick Start Guide - E-Commerce Microservices

## Prerequisites

- Docker and Docker Compose installed (version 20.10+)
- Java 21 or later
- Maven 3.8+
- Git
- Supabase account with a project created

## 5-Minute Setup

### 1. Configure Supabase Credentials

Edit `Backend/.env`:

```bash
cd Backend
vim .env
```

Update these values with your Supabase project credentials:
```env
SUPABASE_URL=https://your-project-id.supabase.co
SUPABASE_ANON_KEY=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
SUPABASE_SERVICE_ROLE_KEY=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Get these from Supabase dashboard:
- Settings → API → Project URL (SUPABASE_URL)
- Settings → API → Anon Key (SUPABASE_ANON_KEY)
- Settings → API → Service Role Key (SUPABASE_SERVICE_ROLE_KEY)

### 2. Start Services

```bash
# From Backend directory
docker-compose up -d

# Wait for services to start (30-60 seconds)
docker-compose ps

# Verify all services are running
docker-compose logs --tail=20
```

### 3. Verify Setup

```bash
# Check PostgreSQL
docker exec ecommerce-postgres pg_isready

# Check RabbitMQ
docker exec ecommerce-rabbitmq rabbitmq-diagnostics ping

# Check Auth Service
curl http://localhost:8081/actuator/health
```

## Test the API

### 1. Sign Up

```bash
curl -X POST http://localhost:8081/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "SecurePass123!",
    "confirmPassword": "SecurePass123!",
    "firstName": "John",
    "lastName": "Doe"
  }'

# Expected response (201):
# {
#   "success": true,
#   "userId": "user_abc123",
#   "email": "john@example.com",
#   "message": "User registered. Please verify your email."
# }
```

### 2. Login

```bash
curl -X POST "http://localhost:8081/api/auth/login?email=john@example.com&password=SecurePass123!" \
  -H "Content-Type: application/json"

# Expected response (200):
# {
#   "success": true,
#   "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
#   "refreshToken": "refresh_token_123",
#   "expiresIn": 3600
# }
```

### 3. Use Token to Call Protected Service

```bash
TOKEN="eyJhbGciOiJIUzUxMiJ9..."

# Call payment service (requires JWT)
curl -X GET http://localhost:8083/api/payments \
  -H "Authorization: Bearer $TOKEN"
```

## Common Commands

```bash
# View logs
docker-compose logs -f auth-service
docker-compose logs -f payment-service
docker-compose logs -f inventory-service

# Stop services
docker-compose stop

# Start services
docker-compose start

# Restart a service
docker-compose restart payment-service

# Remove all services and volumes
docker-compose down -v

# Rebuild services
docker-compose build --no-cache
docker-compose up -d
```

## Service Ports

| Service | Port | Health Check |
|---------|------|--------------|
| Auth Service | 8081 | http://localhost:8081/actuator/health |
| Order Service | 8082 | http://localhost:8082/actuator/health |
| Payment Service | 8083 | http://localhost:8083/actuator/health |
| Inventory Service | 8084 | http://localhost:8084/actuator/health |
| Shipping Service | 8085 | http://localhost:8085/actuator/health |
| Notification Service | 8086 | http://localhost:8086/actuator/health |
| RabbitMQ Management | 15672 | http://localhost:15672 (guest/guest) |
| PostgreSQL | 5432 | psql -h localhost -U postgres -d ecommerce |

## Accessing Services

### Swagger UI Documentation

Each service exposes Swagger documentation:

- Auth Service: http://localhost:8081/swagger-ui.html
- Payment Service: http://localhost:8083/swagger-ui.html
- Order Service: http://localhost:8082/swagger-ui.html
- Inventory Service: http://localhost:8084/swagger-ui.html
- Shipping Service: http://localhost:8085/swagger-ui.html
- Notification Service: http://localhost:8086/swagger-ui.html

### RabbitMQ Management Console

- URL: http://localhost:15672
- Username: `guest`
- Password: `guest`

### PostgreSQL Database

```bash
# Connect to database
docker exec -it ecommerce-postgres psql -U postgres -d ecommerce

# Useful commands
\dn                    # List schemas
\dt schema_name.*      # List tables in schema
SELECT * FROM pg_tables WHERE schemaname LIKE '%_schema';
\q                     # Exit
```

## Troubleshooting

### Services won't start

```bash
# Check docker logs
docker-compose logs

# Check if ports are available
lsof -i :8081  # Check port 8081
lsof -i :5432  # Check PostgreSQL port
lsof -i :5672  # Check RabbitMQ port

# If ports are in use, stop conflicting services or use different ports
```

### Database connection errors

```bash
# Verify PostgreSQL is running and healthy
docker exec ecommerce-postgres pg_isready

# Check database exists
docker exec ecommerce-postgres psql -U postgres -l | grep ecommerce

# View PostgreSQL logs
docker logs ecommerce-postgres
```

### RabbitMQ connection errors

```bash
# Verify RabbitMQ is running
docker exec ecommerce-rabbitmq rabbitmq-diagnostics ping

# View RabbitMQ logs
docker logs ecommerce-rabbitmq

# Reset RabbitMQ (careful - removes all messages)
docker exec ecommerce-rabbitmq rabbitmqctl reset
```

### JWT validation failures

```bash
# Verify JWT_SECRET is set
grep JWT_SECRET Backend/.env

# Check token format
# Should be: Authorization: Bearer <token>

# Verify token not expired
# Check JWT_EXPIRATION value
```

## Development Workflow

### 1. Make Changes to Code

```bash
# Edit service code
vim Backend/auth-service/src/main/java/com/ecommerce/auth/...
```

### 2. Rebuild Service

```bash
# Rebuild specific service
docker-compose build auth-service

# Rebuild all services
docker-compose build
```

### 3. Restart Service

```bash
# Restart to apply changes
docker-compose up -d auth-service

# View logs
docker-compose logs -f auth-service
```

## Environment Variables

All configuration is in `Backend/.env`. Edit this file to change:

```env
# Supabase Settings
SUPABASE_URL=...
SUPABASE_ANON_KEY=...
SUPABASE_SERVICE_ROLE_KEY=...

# Database Settings
POSTGRES_HOST=postgres
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=ecommerce

# JWT Settings
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000

# RabbitMQ Settings
SPRING_RABBITMQ_HOST=rabbitmq
SPRING_RABBITMQ_PORT=5672

# Email Settings (for notifications)
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
```

## Next Steps

1. **Review Architecture:** Read `Backend/ARCHITECTURE_GUIDE.md`
2. **Implement JWT:** Follow `Backend/JWT_VALIDATION_IMPLEMENTATION_GUIDE.md` for each service
3. **Set Up Frontend:** Configure React client to use auth endpoints
4. **Deploy:** Follow production deployment guide
5. **Monitor:** Set up logging and monitoring

## API Examples

### Create Payment

```bash
curl -X POST http://localhost:8083/api/payments \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": 1,
    "amount": 99.99,
    "paymentMethod": "CREDIT_CARD"
  }'
```

### Create Order

```bash
curl -X POST http://localhost:8082/api/orders \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 123,
    "items": [
      {"productId": 1, "quantity": 2}
    ]
  }'
```

### Reserve Inventory

```bash
curl -X POST http://localhost:8084/api/inventory/reserve \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2
  }'
```

## Production Deployment

For production deployment, see `Backend/DEPLOYMENT_GUIDE.md`

## Getting Help

- **Architecture Issues:** See `ARCHITECTURE_GUIDE.md`
- **JWT Setup:** See `JWT_VALIDATION_IMPLEMENTATION_GUIDE.md`
- **Docker Issues:** Check `docker-compose logs`
- **Database Issues:** Run PostgreSQL diagnostics
- **API Documentation:** Visit Swagger UI endpoints

---

**Happy coding! 🚀**
