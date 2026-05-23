#!/bin/bash

echo "Starting Ecommerce Event-Driven System..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "Error: Docker is not running. Please start Docker first."
    exit 1
fi

# Build all services
echo "Building all services..."
mvn clean install -DskipTests

if [ $? -ne 0 ]; then
    echo "Error: Maven build failed. Please check the build logs."
    exit 1
fi

# Start all services with Docker Compose
echo "Starting all services with Docker Compose..."
docker-compose up --build

echo "Services are starting up..."
echo "Access services at:"
echo "  RabbitMQ Management: http://localhost:15672 (guest/guest)"
echo "  Auth Service: http://localhost:8081/swagger-ui.html"
echo "  Order Service: http://localhost:8082/swagger-ui.html"
echo "  Payment Service: http://localhost:8083/swagger-ui.html"
echo "  Inventory Service: http://localhost:8084/swagger-ui.html"
echo "  Shipping Service: http://localhost:8085/swagger-ui.html"
echo "  Notification Service: http://localhost:8086/swagger-ui.html"
echo ""
echo "To stop all services, run: docker-compose down"
echo "To view logs, run: docker-compose logs -f [service-name]"
