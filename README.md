# Microservices Project - Product, Inventory, and Order Management

This project is a microservices-based application developed using **Spring Boot** and **Spring Cloud OpenFeign**. It consists of three microservices:

- **Product Service**: Manages product details.
- **Inventory Service**: Manages inventory and stock availability.
- **Order Service**: Manages orders and checks inventory before placing an order.

### Additional Components
- **Eureka Server**: Service registry for dynamic service discovery.
- **API Gateway**: Centralized entry point for routing requests to the microservices.
- **Config Server**: Centralized configuration management for all services.

---

## 🛠 Tech Stack

- **Backend**: Java, Spring Boot, Spring Data JPA, Spring Cloud OpenFeign, Spring Cloud Netflix Eureka, Spring Cloud Gateway, Spring Cloud Config
- **Database**: MySQL
- **Build Tool**: Maven
- **REST Client**: Postman or any other tool
- **Containerization**: Docker
- **Orchestration**: Kubernetes

---

## 🚀 How to Run Locally

### Prerequisites
- JDK 17+
- Maven
- Docker Desktop with Kubernetes enabled (optional)
- IDE (IntelliJ, Eclipse, or any other)

### Build and Run Each Service

#### 1. Config Server
```bash
cd config
mvn clean install
mvn spring-boot:run
```
- Runs on http://localhost:8888

#### 2. Eureka Server
```bash
cd ../eureka-server
mvn clean install
mvn spring-boot:run
```
- Runs on http://localhost:8761

#### 3. API Gateway
```bash
cd ../api-gateway
mvn clean install
mvn spring-boot:run
```
- Runs on http://localhost:8084

#### 4. Product Service
```bash
cd ../product-service
mvn clean install
mvn spring-boot:run
```
- Runs on http://localhost:8080

#### 5. Inventory Service
```bash
cd ../inventory-service
mvn clean install
mvn spring-boot:run
```
- Runs on http://localhost:8082

#### 6. Order Service
```bash
cd ../order-service
mvn clean install
mvn spring-boot:run
```
- Runs on http://localhost:8081

---

## 📦 Docker Setup

### Build Docker Images
```bash
docker build -t product-service ./product-service
docker build -t inventory-service ./inventory-service
docker build -t order-service ./order-service
docker build -t eureka-server ./eureka-server
docker build -t api-gateway ./api-gateway
docker build -t config-server ./config
```

### Run with Docker Compose
Create a `docker-compose.yml` file:

```yaml
version: '3.8'
services:
  config-server:
    image: config-server
    ports:
      - "8888:8888"
  eureka-server:
    image: eureka-server
    ports:
      - "8761:8761"
  api-gateway:
    image: api-gateway
    ports:
      - "8084:8084"
  product-service:
    image: product-service
    ports:
      - "8080:8080"
  inventory-service:
    image: inventory-service
    ports:
      - "8082:8082"
  order-service:
    image: order-service
    ports:
      - "8081:8081"
```

### Run
```bash
docker-compose up --build
```

---

## 🚀 Kubernetes Setup

### Apply All Kubernetes Resources
```bash
kubectl apply -f k8s-yamls/
```

Ensure you have YAML files for:
- Deployments and Services for each microservice
- ConfigMaps for environment properties
- PersistentVolumeClaims for MySQL
- Eureka and API Gateway services with `LoadBalancer` or `NodePort`

---

## 📆 API Endpoints

### 1. Product Service (http://localhost:8080)

| Method | Endpoint               | Description                     |
|--------|------------------------|---------------------------------|
| **GET**    | /products            | Get all products                  |
| **GET**    | /products/{id}       | Get product by ID                 |
| **POST**   | /products            | Create a new product              |
| **PUT**    | /products/{id}       | Update an existing product        |
| **DELETE** | /products/{id}       | Delete a product                  |
| **GET**    | /products/sku/{skuCode} | Get product by SKU Code        |

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

| Method | Endpoint               | Description                     |
|--------|------------------------|---------------------------------|
| **GET**    | /inventory           | Get all inventory items           |
| **GET**    | /inventory/{id}      | Get inventory by ID               |
| **POST**   | /inventory           | Create new inventory              |
| **DELETE** | /inventory/{id}      | Delete inventory item             |
| **POST**   | /inventory/stock     | Check stock availability          |

#### Check Stock Availability
```http
POST /inventory/stock?skuCode=iphone_13&quantity=10
```

---

### 3. Order Service (http://localhost:8081)

| Method | Endpoint               | Description                     |
|--------|------------------------|---------------------------------|
| **GET**    | /orders              | Get all orders                    |
| **GET**    | /orders/{id}         | Get order by ID                   |
| **POST**   | /orders              | Create a new order                |
| **DELETE** | /orders/{id}         | Delete an order                   |

#### Sample Order JSON
```json
{
    "skuCode": "iphone_13",
    "quantity": 5
}
```

---

## 📚 How It Works

1. **Create a Product**: Add products using the Product Service.
2. **Add Inventory**: Use the Inventory Service to set stock levels.
3. **Place an Order**: The Order Service uses OpenFeign to check inventory and product existence before placing the order.
4. **Stock Check**: Inventory Service verifies if the requested quantity is available.
5. **Order Validation**: Only allows orders if enough stock is present and the product exists.
6. **API Gateway**: Routes all requests through a centralized endpoint.
7. **Eureka Server**: Manages service registration and discovery.
8. **Config Server**: Centralizes and externalizes configuration for all microservices.

---

## 🥬 Testing

1. **Postman**: Use the provided endpoints to test each service.
2. **API Gateway Testing**: Access endpoints via the gateway (e.g., http://localhost:8084/products).

---

## 🛡 Error Handling

- **404 Not Found**: If product not found (ProductNotFoundException in Product Service).
- **400 Bad Request**: When trying to create an order with insufficient stock.
- **204 No Content**: For successful deletion of orders or inventory items.
- **500 Internal Server Error**: Issues with service communication or gateway.

---

## 🔗 Communication Between Services

- **OpenFeign Client** (InventoryClient) is used in the Order Service to call the Inventory Service.
- **OpenFeign Client** (ProductClient) is used in the Order Service to call the Product Service.
- **Eureka Server** enables dynamic service discovery for the API Gateway and all microservices.
- **Spring Cloud Config Server** serves configuration to all microservices.

---

## 💡 Future Enhancements

- Implement Circuit Breaker with Resilience4J.
- Integrate with Kafka for async communication.
- Add Swagger for API documentation.
- Enhance API Gateway with rate-limiting and security features.

---

## 🚦 Troubleshooting

If you encounter the error:
`TransportException: Cannot execute request on any known server`

### Possible Solutions:
1. **Eureka Server Not Running**:
   - Ensure the Eureka Server is running on http://localhost:8761.

2. **Incorrect Eureka Server URL in application.properties**:
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

---

## 🚦 Status Codes

- **200 OK**: Successful retrieval of data.
- **201 Created**: When a new resource is created.
- **204 No Content**: When an item is successfully deleted.
- **400 Bad Request**: For invalid requests (e.g., insufficient stock).
- **404 Not Found**: When a resource (e.g., product, order) is not found.

---
