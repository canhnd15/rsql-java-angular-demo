# Database Setup Guide

This guide will help you set up PostgreSQL and initialize the database with sample data for the RSQL project.

## Option 1: Using Docker (Recommended)

### Prerequisites
- Docker and Docker Compose installed
- Java 21
- Maven 3.6+

### Quick Start
1. **Start the database:**
   ```bash
   docker-compose up -d postgres
   ```

2. **Run the Spring Boot application:**
   ```bash
   cd rsql
   ./mvnw spring-boot:run
   ```

3. **Start the frontend:**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

### Using the startup scripts
- **Windows:** Run `start-project.bat`
- **Linux/Mac:** Run `./start-project.sh`

## Option 2: Manual PostgreSQL Setup

### Prerequisites
- PostgreSQL 15+ installed locally
- Java 21
- Maven 3.6+

### Setup Steps

1. **Create the database:**
   ```sql
   CREATE DATABASE rsql;
   ```

2. **Update connection details in `application.properties` if needed:**
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/rsql
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Run the application:**
   ```bash
   cd rsql
   ./mvnw spring-boot:run
   ```

## Sample Data

The application automatically creates sample data on startup with:

### Orders (10 records)
- Various order statuses (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- Different customer names and emails
- Range of total amounts ($75.25 - $450.00)
- Recent timestamps for testing

### Order Details (15 records)
- Products like Gaming Laptop, Wireless Mouse, 4K Monitor, etc.
- Different quantities and prices
- Linked to the orders above

## Database Schema

### Orders Table
- `id` (UUID, Primary Key)
- `order_number` (VARCHAR, Unique)
- `customer_name` (VARCHAR)
- `customer_email` (VARCHAR)
- `total_amount` (DECIMAL)
- `status` (ENUM: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

### Order Details Table
- `id` (UUID, Primary Key)
- `order_id` (UUID, Foreign Key to orders.id)
- `product_name` (VARCHAR)
- `product_sku` (VARCHAR)
- `quantity` (INTEGER)
- `unit_price` (DECIMAL)
- `total_price` (DECIMAL)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

## Testing the Setup

### 1. Check Database Connection
Visit: `http://localhost:8080/api/orders`

Expected response:
```json
{
  "status": "SUCCESS",
  "message": "Get order list successfully!",
  "data": {
    "content": [...],
    "totalElements": 10,
    "totalPages": 1,
    "size": 10,
    "number": 0,
    "first": true,
    "last": true
  }
}
```

### 2. Test RSQL Filtering
- Filter by status: `http://localhost:8080/api/orders?filters=status=="PENDING"`
- Filter by customer: `http://localhost:8080/api/orders?filters=customerName=like="John"`
- Sort by amount: `http://localhost:8080/api/orders?sorts=totalAmount:desc`

### 3. Test Frontend
Visit: `http://localhost:4200`

You should see:
- Orders page with 10 sample orders
- Order Details page with 15 sample order details
- Working filters and sorting
- Pagination controls

## Troubleshooting

### Database Connection Issues
1. **Check if PostgreSQL is running:**
   ```bash
   docker ps  # For Docker setup
   # or
   pg_isready -h localhost -p 5432  # For local setup
   ```

2. **Check connection details in `application.properties`**

3. **Verify database exists:**
   ```sql
   \l  -- List databases in psql
   ```

### Data Not Loading
1. **Check application logs for SQL errors**
2. **Verify `data.sql` file is in `src/main/resources/`**
3. **Check `spring.sql.init.mode=always` in properties**

### Port Conflicts
- **Backend (8080):** Change `server.port` in `application.properties`
- **Frontend (4200):** Change port in `vite.config.ts`
- **Database (5432):** Change port in `docker-compose.yml`

## Clean Restart

To reset everything and start fresh:

1. **Stop all services:**
   ```bash
   docker-compose down
   # Kill any running Java/Node processes
   ```

2. **Remove Docker volumes (if using Docker):**
   ```bash
   docker-compose down -v
   ```

3. **Start fresh:**
   ```bash
   docker-compose up -d postgres
   cd rsql && ./mvnw spring-boot:run
   cd ../frontend && npm run dev
   ```

## Production Considerations

For production deployment:

1. **Change database credentials**
2. **Use `spring.jpa.hibernate.ddl-auto=validate`**
3. **Remove `spring.sql.init.mode=always`**
4. **Set up proper database backups**
5. **Configure connection pooling**
6. **Use environment variables for sensitive data**

