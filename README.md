## Fitness Microservices System

A scalable, event-driven microservices architecture for tracking user activities, generating AI-based fitness recommendations, and managing secure user profiles using modern Spring Boot ecosystem tools like Spring Cloud, Keycloak, RabbitMQ, and Google Gemini AI.

### Overview

This project is a real-world distributed system that simulates an enterprise-grade fitness platform where:

- Users log fitness activities
- Activity data is validated and stored
- AI service generates personalized recommendations using Google Gemini
- Services communicate via REST + Event-driven messaging
- Centralized authentication is handled by Keycloak
- System is fully scalable using Eureka + API Gateway

### High-Level System Architecture

### System Design

The system follows Microservices Architecture with both:

### Synchronous Communication
- REST-based service-to-service communication
- Used for User validation from Activity Service

### Asynchronous Communication
- RabbitMQ-based event streaming
- Used for AI recommendation generation pipeline

### Microservices Breakdown
### User Service
- Manages user profiles (extension of Keycloak user)
- Stores additional attributes (fitness goals, preferences, etc.)
- Syncs user data from Keycloak

### Activity Service
- Core service for logging user activities
- Validates user via User Service (REST + WebClient)
- Publishes activity events to RabbitMQ

### AI Service
- Consumes activity events from RabbitMQ
- Generates recommendations using Google Gemini API
- Stores AI insights in MongoDB

### API Gateway
- Single entry point for all client requests
- Routes requests to appropriate microservices
- Handles authentication & token validation
- Integrates Keycloak security filters

### Eureka Server
- Service registry for dynamic service discovery
- Enables load balancing between services
- Eliminates hardcoded service URLs

### Config Server
- Centralized configuration management
- Stores all microservice configurations in one place
- Supports environment-based config loading

### Keycloak Server
- Handles authentication & authorization
- Implements OAuth2 + PKCE Flow
- Issues JWT access & refresh tokens
- Manages roles and user identity

### RabbitMQ
- Message broker for async communication
- Decouples Activity Service and AI Service
- Ensures fault tolerance and scalability

### Security Architecture
- OAuth2 Authentication via Keycloak
- JWT-based stateless security
- API Gateway acts as resource server
- Public key verification using JWKS endpoint
- PKCE flow for secure frontend authentication

### Tech Stack
### Backend
- Java 21
- Spring Boot
- Spring Cloud Gateway
- Spring Security
- Spring WebFlux (Reactive WebClient)
  
### Microservices
- Cloud Netflix Eureka
- Spring Cloud Config Server
- OpenFeign / WebClient

### Messaging
- RabbitMQ

### Databases
- PostGreSQL (User)
- MongoDB (Activity Service, AI recommendations)

### Authentication
- Keycloak (OAuth2, JWT, PKCE)

### AI Integration
- Google Gemini API

### DevOps / Tools
- Docker
- Maven
- Postman
- GitHub

### Communication Flow
### 1️⃣ User Authentication Flow
```bash
Frontend → Keycloak → JWT Token → API Gateway → Microservices
```
### 2️⃣ Activity Creation Flow (Sync + Async)
```bash
Client → API Gateway → Activity Service
                     ↓
              User Service (REST validation)

Activity Saved → RabbitMQ Queue → AI Service
```
### 3️⃣ AI Recommendation Flow
```bash
RabbitMQ Consumer → Gemini API → Process Recommendations → MongoDB
```

### Key Features
- Secure OAuth2 + PKCE authentication
- Fully decoupled microservices architecture
- Async event-driven processing using RabbitMQ
- AI-powered fitness recommendations (Gemini API)
- Service discovery with Eureka
- Centralized config management
- API Gateway routing system
- Scalable database separation (SQL + NoSQL)

### Run RabbitMQ (Docker)
```bash
docker run -d --name rabbitmq \
-p 5672:5672 -p 15672:15672 \
rabbitmq:3-management
```
Access dashboard:
```bash
http://localhost:15672
```

### PostgreSQL (Docker)
```bash
docker run -d --name postgres-db \
-e POSTGRES_USER=admin \
-e POSTGRES_PASSWORD=admin123 \
-e POSTGRES_DB=fitness_db \
-p 5432:5432 \
postgres:16
```
### pgAdmin (Docker)
```bash
docker run -d --name pgadmin \
-e PGADMIN_DEFAULT_EMAIL=admin@admin.com \
-e PGADMIN_DEFAULT_PASSWORD=admin123 \
-p 5050:80 \
dpage/pgadmin4
```
Access pgAdmin UI
```bash
http://localhost:5050
```
### Login
Email: admin@admin.com
Password: admin123

### Configuration Strategy
- All configs stored in Config Server
- Services fetch configs at runtime
- Environment-based profiles supported (dev, prod)

### Key Design Principles Used
- ✔ Microservices decomposition
- ✔ Event-driven architecture
- ✔ API Gateway pattern
- ✔ Centralized configuration
- ✔ Distributed authentication
- ✔ Database per service pattern
