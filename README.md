

# Microservices Project - Product, Inventory, and Order Management

This project is a microservices-based application developed using *Spring Boot* and *Spring Cloud OpenFeign*. It consists of three microservices:

- *Product Service*: Manages product details.
- *Inventory Service*: Manages inventory and stock availability.
- *Order Service*: Manages orders and checks inventory before placing an order.

### Additional Components
- *Eureka Server*: Service registry for dynamic service discovery.
- *API Gateway*: Centralized entry point for routing requests to the microservices.

---

## 🛠 Tech Stack

- *Backend*: Java, Spring Boot, Spring Data JPA, Spring Cloud OpenFeign, Spring Cloud Netflix Eureka, Spring Cloud Gateway
- *Database*: MySQL
- *Build Tool*: Maven
- *REST Client*: Postman or any other tool

---

## 🚀 How to Run Locally

### Prerequisites
- JDK 17+
- Maven
- IDE (IntelliJ, Eclipse, or any other)

### Build and Run Each Service

#### 1. Eureka Server
```bash
cd eureka-server
mvn clean install
mvn spring-boot:run
```
- Runs on http://localhost:8761

#### 2. API Gateway
```bash
cd ../api-gateway
mvn clean install
mvn spring-boot:run
```
- Runs on http://localhost:8083

#### 3. Product Service
```bash
cd ../product-service
mvn clean install
mvn spring-boot:run
```
- Runs on http://localhost:8080

#### 4. Inventory Service
```bash
cd ../inventory-service
mvn clean install
mvn spring-boot:run
```
- Runs on http://localhost:8082

#### 5. Order Service
```bash
cd ../order-service
mvn clean install
mvn spring-boot:run
```
- Runs on http://localhost:8081

---

## 📦 API Endpoints

### 1. Product Service (http://localhost:8080)

| Method | Endpoint         | Description                |
|--------|------------------|----------------------------|
| GET    | /products        | Get all products           |
| GET    | /products/{id}   | Get product by ID          |
| POST   | /products        | Create a new product       |
| PUT    | /products/{id}   | Update an existing product |
| DELETE | /products/{id}   | Delete a product           |
| GET    | /products/sku/{skuCode} | Check for product by SKU |

#### Sample Product JSON
```json
{
    "name": "iPhone 13",
    "description": "Latest Apple iPhone",
    "price": 999.99,
    "quantity": 50
}
```

---

### 2. Inventory Service (http://localhost:8082)

| Method | Endpoint              | Description                      |
|--------|-----------------------|----------------------------------|
| GET    | /inventory            | Get all inventory items          |
| GET    | /inventory/{id}       | Get inventory by ID              |
| POST   | /inventory            | Create new inventory             |
| DELETE | /inventory/{id}       | Delete inventory item            |
| POST   | /inventory/stock      | Check stock availability         |

#### Check Stock Availability
```http
POST /inventory/stock?skuCode=iphone_13&quantity=10
```

---

### 3. Order Service (http://localhost:8081)

| Method | Endpoint       | Description                |
|--------|----------------|----------------------------|
| GET    | /orders        | Get all orders             |
| GET    | /orders/{id}   | Get order by ID            |
| POST   | /orders        | Create a new order         |
| DELETE | /orders/{id}   | Delete an order            |

#### Sample Order JSON
```json
{
    "skuCode": "iphone_13",
    "quantity": 5
}
```

---

## 📚 How It Works

1. *Create a Product*: Add products using the Product Service.
2. *Add Inventory*: Use the Inventory Service to set stock levels.
3. *Place an Order*: The Order Service uses OpenFeign to check inventory before placing the order.
4. *Stock Check*: Inventory Service verifies if the requested quantity is available.
5. *Order Validation*: Only allows orders if enough stock is present.
6. *Product Validation*: Only allows orders if product exists.
7. *API Gateway*: Routes all requests through a centralized endpoint.
8. *Eureka Server*: Manages service registration and discovery.

---

## 🧪 Testing

1. *Postman*: Use the provided endpoints to test each service.

---

## 🛡 Error Handling

- *404 Not Found*: If product not found.
- *400 Bad Request*: When trying to create an order with insufficient stock.
- *500 Internal Server Error*: Issues with service communication or gateway.

---

## 🔗 Communication Between Services

- *OpenFeign Client* (InventoryClient) is used in the Order Service to call the Inventory Service.
- *OpenFeign Client* (ProductClient) is used in the Order Service to call the Product Service.
- *Eureka Server* enables dynamic service discovery for the API Gateway and all microservices.

---

## 💡 Future Enhancements

- Implement Circuit Breaker with Resilience4J.
- Integrate with Kafka for async communication.
- Add Swagger for API documentation.
- Improve API Gateway with rate-limiting and security features.

---

## 🛠 Troubleshooting

If you encounter the error:
```
TransportException: Cannot execute request on any known server
```

### Possible Solutions:
1. **Eureka Server Not Running**:
   - Ensure the Eureka Server is running on `http://localhost:8761`.

2. **Incorrect Eureka Server URL in `application.properties`**:
```properties
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

3. **Cross-Service Communication Issues**:
   - Make sure all services are registered in Eureka.
   - Check that the API Gateway is properly routing requests.

4. **Clear Cache and Rebuild**:
```bash
mvn clean install
```

