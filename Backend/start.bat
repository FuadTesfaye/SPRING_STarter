@echo off
echo =====================================================
echo   Event Ticket Booking Platform - Starting Backend
echo =====================================================
echo.
echo Building and starting all 6 microservices + RabbitMQ...
docker compose up --build -d
echo.
echo Endpoints:
echo   Auth Service:         http://localhost:8081/api/auth/health
echo   Event Service:        http://localhost:8082/api/events/health
echo   Booking Service:      http://localhost:8083/api/bookings/health
echo   Payment Service:      http://localhost:8084/api/payments/health
echo   Seat Service:         http://localhost:8085/api/seats/health
echo   Notification Service: http://localhost:8086/api/notifications/health
echo   RabbitMQ UI:          http://localhost:15672 (guest/guest)
echo.
docker compose logs -f
