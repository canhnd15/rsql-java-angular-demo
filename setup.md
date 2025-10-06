# RSQL Project Setup Guide

This project consists of a Spring Boot backend with RSQL support and an Angular 18 frontend with Vite.

## Backend Setup (Spring Boot)

### Prerequisites
- Java 21
- Maven 3.6+
- PostgreSQL database

### Database Setup
1. Create a PostgreSQL database named `rsql`
2. Update `application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/rsql
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Running the Backend
```bash
cd rsql
./mvnw spring-boot:run
```

The backend will be available at `http://localhost:8080`

### API Endpoints
- **Orders**: `GET /api/orders`
- **Order Details**: `GET /api/order-details`

Both endpoints support RSQL filtering and sorting:
- `filters` parameter for RSQL filtering
- `sorts` parameter for sorting
- `page` and `size` for pagination

## Frontend Setup (Angular 18 + Vite)

### Prerequisites
- Node.js 18+
- npm or yarn

### Installation and Running
```bash
cd frontend
npm install
npm run dev
```

The frontend will be available at `http://localhost:4200`

## Features

### Backend Features
- **Order Entity**: Complete order management with status tracking
- **OrderDetail Entity**: Order line items with product information
- **RSQL Integration**: Advanced filtering and sorting
- **RESTful APIs**: Clean, consistent API design
- **Pagination**: Efficient data loading with Spring Data JPA

### Frontend Features
- **Modern UI**: Clean, responsive design
- **Real-time Filtering**: Instant search and filter
- **Advanced Sorting**: Multiple sort options
- **Pagination**: Efficient data browsing
- **RSQL Integration**: Full RSQL syntax support

## Sample Data

To test the application, you can insert sample data into your database:

```sql
-- Sample Orders
INSERT INTO orders (id, order_number, customer_name, customer_email, total_amount, status, created_at, updated_at) VALUES
('550e8400-e29b-41d4-a716-446655440001', 'ORD-001', 'John Doe', 'john@example.com', 299.99, 'PENDING', NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440002', 'ORD-002', 'Jane Smith', 'jane@example.com', 149.50, 'CONFIRMED', NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440003', 'ORD-003', 'Bob Johnson', 'bob@example.com', 89.99, 'SHIPPED', NOW(), NOW());

-- Sample Order Details
INSERT INTO order_details (id, order_id, product_name, product_sku, quantity, unit_price, total_price, created_at, updated_at) VALUES
('550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440001', 'Laptop', 'LAP-001', 1, 299.99, 299.99, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440012', '550e8400-e29b-41d4-a716-446655440002', 'Mouse', 'MOU-001', 2, 25.00, 50.00, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440013', '550e8400-e29b-41d4-a716-446655440002', 'Keyboard', 'KEY-001', 1, 99.50, 99.50, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440014', '550e8400-e29b-41d4-a716-446655440003', 'Headphones', 'HEA-001', 1, 89.99, 89.99, NOW(), NOW());
```

## RSQL Examples

### Filtering Examples
- `customerName=like="John"` - Find orders by customer name containing "John"
- `status=="PENDING"` - Find all pending orders
- `totalAmount=gt=100` - Find orders with total amount greater than 100
- `status=="PENDING";totalAmount=gt=100` - Find pending orders with total > 100

### Sorting Examples
- `createdAt:desc` - Sort by creation date (newest first)
- `totalAmount:asc` - Sort by total amount (lowest first)
- `customerName:asc` - Sort by customer name (A-Z)

## Development

### Backend Development
- Uses Spring Boot 3.5.6
- Java 21 with modern features
- Lombok for reducing boilerplate
- RSQL-JPA for advanced querying
- PostgreSQL for data persistence

### Frontend Development
- Angular 18 with standalone components
- Vite for fast development and building
- TypeScript for type safety
- SCSS for styling
- RxJS for reactive programming

