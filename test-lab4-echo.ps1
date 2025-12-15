# Test script to verify Lab4 echo functionality
Write-Host "Testing Lab4 Echo Functionality" -ForegroundColor Cyan
Write-Host ""

# Check if server is running
$serverRunning = Test-NetConnection -ComputerName localhost -Port 5000 -InformationLevel Quiet -WarningAction SilentlyContinue

if (-not $serverRunning) {
    Write-Host "ERROR: Server is not running on port 5000" -ForegroundColor Red
    Write-Host "Please start the server first with:" -ForegroundColor Yellow
    Write-Host "  .\gradlew :lab4-p2p:run --args=`"server 5000`"" -ForegroundColor Yellow
    exit 1
}

Write-Host "✓ Server is running on port 5000" -ForegroundColor Green
Write-Host ""
Write-Host "To test message echo:" -ForegroundColor Yellow
Write-Host "1. Keep the server running in one terminal" -ForegroundColor White
Write-Host "2. In another terminal, run:" -ForegroundColor White
Write-Host "   .\gradlew :lab4-p2p:run --args=`"client localhost 5000`"" -ForegroundColor Cyan
Write-Host "3. Type a message and press Enter" -ForegroundColor White
Write-Host "4. You should see the echo response" -ForegroundColor White
Write-Host ""
Write-Host "Expected behavior:" -ForegroundColor Yellow
Write-Host "  - Type: Hello" -ForegroundColor White
Write-Host "  - See: client receives echo: Hello" -ForegroundColor Green
Write-Host "  - Server shows: server recv: Hello" -ForegroundColor Green

