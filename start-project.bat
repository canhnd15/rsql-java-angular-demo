@echo off
echo Starting RSQL Project...

echo.
echo 1. Starting PostgreSQL database with Docker...
docker-compose up -d postgres

echo.
echo 2. Waiting for database to be ready...
timeout /t 10 /nobreak > nul

echo.
echo 3. Starting Spring Boot application...
cd rsql
start cmd /k "mvnw spring-boot:run"

echo.
echo 4. Starting Angular frontend...
cd ..\frontend
start cmd /k "npm install && npm start"

echo.
echo Project started successfully!
echo.
echo Backend: http://localhost:8080
echo Frontend: http://localhost:4200
echo Database: localhost:5432
echo.
echo Press any key to stop all services...
pause > nul

echo.
echo Stopping services...
docker-compose down
taskkill /f /im java.exe 2>nul
taskkill /f /im node.exe 2>nul

echo.
echo All services stopped.
pause

