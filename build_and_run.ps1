# PowerShell script to build all Maven modules and start Docker Compose

Write-Host "=========================================" -ForegroundColor Cyan
Write-Host " Building Java Microservices with Maven" -ForegroundColor Cyan
Write-Host "=========================================" -ForegroundColor Cyan

$mvnCmd = "mvn"

# Check if Maven is available
try {
    $null = Get-Command mvn -ErrorAction Stop
} catch {
    Write-Host "Maven is not globally installed. Downloading a local portable version..." -ForegroundColor Yellow
    
    $mavenDir = "$PWD\.maven"
    $mavenZip = "$mavenDir\maven.zip"
    $mavenHome = "$mavenDir\apache-maven-3.9.6"
    
    if (-not (Test-Path $mavenHome)) {
        New-Item -ItemType Directory -Force -Path $mavenDir | Out-Null
        Write-Host "Downloading Maven 3.9.6..." -ForegroundColor Yellow
        Invoke-WebRequest -Uri "https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip" -OutFile $mavenZip
        Write-Host "Extracting Maven..." -ForegroundColor Yellow
        Expand-Archive -Path $mavenZip -DestinationPath $mavenDir -Force
        Remove-Item $mavenZip
    }
    
    $mvnCmd = "$mavenHome\bin\mvn.cmd"
    Write-Host "Using portable Maven at $mvnCmd" -ForegroundColor Green
}

# Build the root project (which builds all modules)
Write-Host "Running '$mvnCmd clean package -DskipTests'..." -ForegroundColor Yellow
& $mvnCmd clean package -DskipTests

if ($LASTEXITCODE -ne 0) {
    Write-Host "Maven build failed! Please check the output above." -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host "`n=========================================" -ForegroundColor Cyan
Write-Host " Starting Docker Compose" -ForegroundColor Cyan
Write-Host "=========================================" -ForegroundColor Cyan

docker-compose up --build
