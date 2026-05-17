\# Event-Driven Microservices System



A distributed event-driven system built with Spring Boot, RabbitMQ, and Onion Architecture.



\## Architecture



\- \*\*Auth Service\*\* (8081): User registration, login, JWT authentication

\- \*\*Order Service\*\* (8082): Order creation and management

\- \*\*Payment Service\*\* (8083): Payment processing

\- \*\*Inventory Service\*\* (8084): Stock management

\- \*\*Shipping Service\*\* (8085): Shipment creation

\- \*\*Notification Service\*\* (8086): Event logging and notifications



\## Event Flow



1\. User registers → Auth publishes `user.registered`

2\. User creates order → Order publishes `order.created`

3\. Payment + Inventory process in parallel

4\. If both succeed → Shipping creates shipment → publishes `shipment.created`

5\. Notification Service logs all events



\## Tech Stack



\- Java 21

\- Spring Boot 3.4.0

\- RabbitMQ

\- H2 Database

\- Docker

\- Swagger/OpenAPI



\## Prerequisites



\- Java 21+

\- Docker Desktop

\- Maven



\## Quick Start



1\. Start RabbitMQ:

```bash

cd docker-config

docker-compose up -d

