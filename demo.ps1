# ============================================
# EVENT-DRIVEN SYSTEM - LIVE DEMO SCRIPT
# ============================================

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  EVENT-DRIVEN MICROSERVICES DEMO" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# DEMO 1: Register a New User
Write-Host "📝 DEMO 1: Registering a new user..." -ForegroundColor Green
Write-Host ""
$body = @{
    username = "demo_user"
    email = "demo@example.com"
    password = "password123"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/register" -Method Post -Body $body -ContentType "application/json"
Write-Host "✅ Registration Successful!" -ForegroundColor Green
$response | ConvertTo-Json
Write-Host ""

# Save the token
$token = $response.token
$userId = $response.userId

# DEMO 2: Login
Write-Host "🔑 DEMO 2: Logging in..." -ForegroundColor Green
Write-Host ""
$loginBody = @{
    email = "demo@example.com"
    password = "password123"
} | ConvertTo-Json

$loginResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/login" -Method Post -Body $loginBody -ContentType "application/json"
Write-Host "✅ Login Successful!" -ForegroundColor Green
$loginResponse | ConvertTo-Json
Write-Host ""

# DEMO 3: Validate Token
Write-Host "🔒 DEMO 3: Validating JWT Token..." -ForegroundColor Green
Write-Host ""
$headers = @{
    Authorization = "Bearer $token"
}
$validateResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/validate" -Method Get -Headers $headers
Write-Host "✅ Token is Valid!" -ForegroundColor Green
$validateResponse | ConvertTo-Json
Write-Host ""

# DEMO 4: Try Duplicate Registration (Error Handling)
Write-Host "❌ DEMO 4: Testing duplicate registration (should fail)..." -ForegroundColor Yellow
Write-Host ""
try {
    Invoke-RestMethod -Uri "http://localhost:8081/api/auth/register" -Method Post -Body $body -ContentType "application/json"
} catch {
    Write-Host "✅ Correctly rejected duplicate email!" -ForegroundColor Green
    Write-Host "Error: $_" -ForegroundColor Red
}
Write-Host ""

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  DEMO COMPLETED SUCCESSFULLY! 🎉" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Cyan