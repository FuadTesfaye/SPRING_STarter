@echo off
echo Building and Testing Ecommerce Event-Driven System...

REM Check if Docker is running
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Docker is not running. Please start Docker first.
    pause
    exit /b 1
)

REM Build all services
echo Building all services...
call mvn clean install -DskipTests

if %errorlevel% neq 0 (
    echo Error: Maven build failed. Please check build logs.
    pause
    exit /b 1
)

echo Build successful!

REM Run integration tests
echo Running integration tests...
cd integration-tests
call mvn test

if %errorlevel% equ 0 (
    echo All tests passed!
) else (
    echo Some tests failed. Please check test logs.
    pause
    exit /b 1
)

echo Build and test completed successfully!
echo To start the system, run: start.bat
pause
