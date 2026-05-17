# Service Endpoints and Run Commands

## Auth Service
Path: `Auth_service`
Port: `8081`

Endpoints:
- `POST http://localhost:8081/api/auth/register`
  - Body: `application/json`
  - Example:
    ```json
    {
      "fullName": "Jane Doe",
      "email": "jane@example.com",
      "password": "P@ssword123"
    }
    ```
- `POST http://localhost:8081/api/auth/login`
  - Body: `application/json`
  - Example:
    ```json
    {
      "email": "jane@example.com",
      "password": "P@ssword123"
    }
    ```
- `GET http://localhost:8081/h2-console`
  - H2 console available at `/h2-console`
  - JDBC URL: `jdbc:h2:mem:authdb`

Run:
```powershell
cd C:\Users\h\SPRING_STarter\Auth_service
.\mvnw.cmd spring-boot:run
```

---

## Inventory Service
Path: `Inventory_service`
Port: `8085`

Endpoints:
- `GET http://localhost:8085/api/health`
  - Response:
    ```json
    {"service":"inventory-service","status":"UP"}
    ```

Run:
```powershell
cd C:\Users\h\SPRING_STarter\Inventory_service
.\mvnw.cmd spring-boot:run
```

---

## Notification Service
Path: `Notification_service`
Port: `8087`

Endpoints:
- `GET http://localhost:8087/api/health`
  - Response:
    ```json
    {"service":"notification-service","status":"UP"}
    ```

Run:
```powershell
cd C:\Users\h\SPRING_STarter\Notification_service
.\mvnw.cmd spring-boot:run
```

Note: this service tries to connect to RabbitMQ on `localhost:5672`. If RabbitMQ is not available, the app still starts but will log connection warnings.

---

## Order Service
Path: `Order_service`
Port: `8083`

Endpoints:
- `GET http://localhost:8083/api/health`
  - Response:
    ```json
    {"service":"order-service","status":"UP"}
    ```
- `POST http://localhost:8083/api/orders`
  - Body: `application/json`
  - Example:
    ```json
    {
      "userId": "user-1",
      "productId": "product-1",
      "quantity": 2,
      "unitPrice": 12.5
    }
    ```
  - Example success response:
    ```json
    {
      "orderId": "...",
      "status": "CREATED",
      "totalPrice": 25.0
    }
    ```
- `GET http://localhost:8083/h2-console`
  - JDBC URL: `jdbc:h2:mem:orderdb`

Run:
```powershell
cd C:\Users\h\SPRING_STarter\Order_service
.\mvnw.cmd spring-boot:run
```

---

## Payment Service
Path: `Payment_service`
Port: `8084`

Endpoints:
- `GET http://localhost:8084/api/health`
  - Response:
    ```json
    {"service":"payment-service","status":"UP"}
    ```

Run:
```powershell
cd C:\Users\h\SPRING_STarter\Payment_service
.\mvnw.cmd spring-boot:run
```

Note: this service also tries to connect to RabbitMQ on `localhost:5672` and will log connection warnings if the broker is unavailable.
