#!/bin/bash

echo "Starting RSQL Project..."

echo ""
echo "1. Starting PostgreSQL database with Docker..."
docker-compose up -d postgres

echo ""
echo "2. Waiting for database to be ready..."
sleep 10

echo ""
echo "3. Starting Spring Boot application..."
cd rsql
gnome-terminal -- bash -c "mvnw spring-boot:run; exec bash" 2>/dev/null || xterm -e "mvnw spring-boot:run" 2>/dev/null || echo "Please start the Spring Boot application manually: cd rsql && ./mvnw spring-boot:run"

echo ""
echo "4. Starting Angular frontend..."
cd ../frontend
gnome-terminal -- bash -c "npm install && npm start; exec bash" 2>/dev/null || xterm -e "npm install && npm start" 2>/dev/null || echo "Please start the Angular application manually: cd frontend && npm install && npm start"

echo ""
echo "Project started successfully!"
echo ""
echo "Backend: http://localhost:8080"
echo "Frontend: http://localhost:4200"
echo "Database: localhost:5432"
echo ""
echo "Press Ctrl+C to stop all services..."

# Function to cleanup on exit
cleanup() {
    echo ""
    echo "Stopping services..."
    docker-compose down
    pkill -f "spring-boot:run" 2>/dev/null
    pkill -f "npm start" 2>/dev/null
    echo ""
    echo "All services stopped."
    exit 0
}

# Set trap to cleanup on script exit
trap cleanup SIGINT SIGTERM

# Wait for user to stop
wait

