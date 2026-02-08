# ProdManager API

Backend API designed as a real-world production management system.

ProdManager is a RESTful API built with Spring Boot that simulates and manages an industrial production flow, including
products, raw materials, stock control, production simulation and financial reporting.

The project was designed with real-world architecture concerns such as authentication, authorization, business rules,
database migrations and containerization.

---

## Tech Stack

- Java 17
- Spring Boot 3.5.10
- Spring Security + JWT
- Spring Data JPA
- Flyway (database migrations)
- Oracle Database (XE)
- Docker & Docker Compose
- OpenAPI / Swagger

---

## Main Features

- User authentication and authorization (JWT)
- Role-based access control (ADMIN / USER)
- Product management
- Raw material management
- Product x raw material relationship
- Stock control
- Production simulation algorithm
- Financial production report
- Input validation and global exception handling
- Unit and integration tests
- Fully documented REST API (Swagger)

---

## API Base URL

```
http://localhost:8080/api/v1
```

---

## Authentication

The API uses JWT (JSON Web Token).

### Register

```
POST /auth/register
```

### Login

```
POST /auth/login
```

The login returns a token:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Use it in all protected endpoints:

```
Authorization: Bearer <TOKEN>
```

---

## Roles

| Role  | Description        |
|-------|--------------------|
| ADMIN | Full system access |
| USER  | Read-only access   |

Most write operations require `ADMIN`.

---

## Main Endpoints

### Products

```
POST    /products
GET     /products
GET     /products/{id}
PUT     /products/{id}
DELETE  /products/{id}
```

### Raw Materials

```
POST    /raw-materials
GET     /raw-materials
GET     /raw-materials/{id}
PUT     /raw-materials/{id}
DELETE  /raw-materials/{id}
PUT     /raw-materials/{id}/stock
```

### Product x Raw Material

```
POST /products/{productId}/materials
```

### Production

```
GET /production/simulate
GET /production/report
```

---

## Production Algorithm

The core of the system is the production simulation algorithm.

It:

1. Reads available stock of raw materials.
2. Calculates how many units of each product can be produced.
3. Prioritizes products by higher value.
4. Deducts raw materials.
5. Returns a production plan and total revenue.

This simulates a real manufacturing decision process.

---

## Running with Docker (Recommended)

Requirements:

- Docker
- Docker Compose

Start everything with:

```bash
docker-compose up --build
```

This will start:

- Oracle Database
- Backend API

---

## Running Locally (Without Docker)

Requirements:

- Java 17+
- Oracle XE running locally

Set profile:

```bash
export SPRING_PROFILES_ACTIVE=dev
```

Run:

```bash
mvn spring-boot:run
```

---

## Swagger (API Documentation)

Swagger UI is available at:

```
http://localhost:8080/swagger-ui.html
```

All endpoints can be tested directly through the browser.

---

## Database Migrations

The project uses Flyway for schema versioning.

All migrations are located in:

```
src/main/resources/db/migration
```

The database is created and updated automatically on startup.

---

## Project Structure

```
controller   → REST endpoints  
service      → Business logic  
repository   → Data access  
model        → JPA entities  
dto          → Request/response objects  
infra        → Security, config, filters  
```

---

## Future Improvements

- Redis cache layer
- Metrics and monitoring
- Cloud deployment (AWS / Render / Railway)
- Frontend integration

---

## Author

Vítor Cavalcante Souza  
Java / Backend Developer

LinkedIn: https://www.linkedin.com/in/vitorcavalcantesouza  
GitHub: https://github.com/Vitor-C-Souza
