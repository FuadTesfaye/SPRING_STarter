# PowerShell script to run all microservices and storefront
$javaServices = @("auth-service", "order-service", "payment-service", "inventory-service", "shipping-service", "notification-service", "product-service", "gateway-service")

foreach ($service in $javaServices) {
    Write-Host "Starting $service..." -ForegroundColor Cyan
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$service'; mvn spring-boot:run"
}

Write-Host "Starting Storefront..." -ForegroundColor Magenta
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'storefront'; npm run dev"

Write-Host "All services are starting in separate windows." -ForegroundColor Green
Write-Host "Storefront: http://localhost:3000" -ForegroundColor White
Write-Host "Gateway: http://localhost:8080" -ForegroundColor White
Write-Host "RabbitMQ: http://localhost:15672" -ForegroundColor Yellow
