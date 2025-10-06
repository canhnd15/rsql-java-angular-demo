# RSQL Frontend

This is an Angular 18 application with Vite for managing Orders and Order Details.

## Features

- **Orders Management**: View, filter, and sort orders
- **Order Details Management**: View, filter, and sort order details
- **RSQL Integration**: Advanced filtering and sorting using RSQL syntax
- **Responsive Design**: Modern, clean UI with responsive layout
- **Real-time Filtering**: Instant filtering as you type

## Getting Started

### Prerequisites

- Node.js (version 18 or higher)
- npm or yarn

### Installation

1. Install dependencies:
```bash
npm install
```

2. Start the development server:
```bash
npm run dev
```

The application will be available at `http://localhost:4200`.

### Building for Production

```bash
npm run build
```

## API Integration

The frontend connects to the Spring Boot backend API:

- **Orders API**: `/api/orders`
- **Order Details API**: `/api/order-details`

The backend should be running on `http://localhost:8080` for the proxy to work correctly.

## RSQL Filtering

The application supports RSQL filtering syntax:

- **Equals**: `status=="PENDING"`
- **Like**: `customerName=like="John"`
- **Greater than**: `totalAmount=gt=100`
- **Less than**: `totalAmount=lt=500`
- **Multiple conditions**: `status=="PENDING";totalAmount=gt=100`

## Sorting

Supports sorting by any field with direction:

- **Ascending**: `createdAt:asc`
- **Descending**: `totalAmount:desc`

