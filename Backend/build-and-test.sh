#!/bin/bash

echo "Building and Testing Ecommerce Event-Driven System..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "Error: Docker is not running. Please start Docker first."
    exit 1
fi

# Build all services
echo "Building all services..."
mvn clean install -DskipTests

if [ $? -ne 0 ]; then
    echo "Error: Maven build failed. Please check build logs."
    exit 1
fi

echo "Build successful!"

# Run integration tests
echo "Running integration tests..."
cd integration-tests
mvn test

if [ $? -eq 0 ]; then
    echo "All tests passed!"
else
    echo "Some tests failed. Please check test logs."
    exit 1
fi

echo "Build and test completed successfully!"
echo "To start the system, run: ./start.sh"
