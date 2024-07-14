# E-Commerce Product Management System

## Table of Contents
1. [Overview](#overview)
2. [Features](#features)
3. [Technical Stack](#technical-stack)
4. [Project Structure](#project-structure)
5. [Setup and Installation](#setup-and-installation)
6. [API Endpoints](#api-endpoints)
7. [Configuration](#configuration)
8. [Monitoring](#monitoring)
9. [UML Diagrams](#uml-diagrams)
10. [Contributing](#contributing)

## Overview
This project is a full-stack e-commerce product management system built with Spring Boot for the backend and React for the frontend. It provides functionality for managing products and categories, including CRUD operations, search capabilities, and a unique binary tree implementation for category management.

## Features
- CRUD operations for products and categories
- Product search with pagination
- Category management using binary tree structure
- Spring Boot profiles for development and production environments
- Actuator for application monitoring and management
- Custom complex queries for advanced product filtering
- Interceptor for request/response logging

## Technical Stack
### Backend
- Java 11
- Spring Boot 2.x
- Spring Data JPA
- H2 Database (Dev) / MySQL (Prod)
- Maven

### Frontend
- React 17.x
- Axios for API calls
- React Router for navigation
- React Bootstrap for UI components

## Project Structure

```plaintext

src/
├── main/
│   ├── java/com/abmike/ecommerce_prod_mgmt/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── util/
│   │   └── EcommerceProdMgmtApplication.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
├── test/
└── frontend/
├── src/
│   ├── components/
│   ├── App.js
│   └── index.js
└── package.json
```

## Setup and Installation
1. Clone the repository: git clone https://github.com/yourusername/ecommerce-prod-mgmt.git

2. Backend Setup:
- Navigate to the project root directory
- Run `mvn clean install` to build the project
- Run `mvn spring-boot:run` to start the server
3. Frontend Setup:
- Navigate to the `frontend` directory
- Run `npm install` to install dependencies
- Run `npm start` to start the development server

## API Endpoints
- Products:
- GET /api/products
- POST /api/products
- GET /api/products/{id}
- PUT /api/products/{id}
- DELETE /api/products/{id}
- Categories:
- GET /api/categories
- POST /api/categories
- GET /api/categories/{id}
- PUT /api/categories/{id}
- DELETE /api/categories/{id}
- Product Search: GET /api/products/search?query=example
- Products by Price Range: GET /api/products/price-range?minPrice=10&maxPrice=100
- Product Count by Category: GET /api/products/count-by-category

## Configuration
- Development profile: `application-dev.properties`
- Production profile: `application-prod.properties`

To run with a specific profile: mvn spring-boot:run -Dspring-boot.run.profiles=dev

## Monitoring
Access actuator endpoints at `/actuator` for application health, metrics, and info.

## UML Diagrams
UML diagrams can be found in the `uml-diagrams` directory in the project root. These include:
- Class Diagram: Illustrates the structure and relationships of the main classes in the system.
- Sequence Diagram: Shows the interaction between components during a product search operation.

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.