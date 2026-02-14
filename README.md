# ProdManager API

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-green?style=for-the-badge&logo=springboot)
![Redis](https://img.shields.io/badge/Redis-Latest-red?style=for-the-badge&logo=redis)
![Oracle](https://img.shields.io/badge/Oracle-21c-red?style=for-the-badge&logo=oracle)
![Docker](https://img.shields.io/badge/Docker-Latest-blue?style=for-the-badge&logo=docker)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

A comprehensive **RESTful API** for industrial production management built with **Spring Boot**. This system simulates real-world manufacturing workflows, managing products, raw materials, inventory control, production planning, and financial reporting.

## Overview

ProdManager is designed with enterprise-grade architecture principles including secure authentication, role-based authorization, complex business rules, automated database migrations, caching strategies, and containerized deployment. It's a perfect reference for building production-ready backend systems.

---

## ✨ Key Features

- 🔐 **User Authentication & Authorization** - JWT-based token authentication
- 👥 **Role-Based Access Control** - ADMIN and USER roles with granular permissions
- 📦 **Product Management** - Create, read, update, and delete products
- 🔧 **Raw Material Management** - Complete lifecycle management of raw materials
- 📊 **Stock Control System** - Real-time inventory tracking and management
- ⚙️ **Production Simulation** - Intelligent algorithm for production planning optimization
- 💰 **Financial Reporting** - Production cost analysis and revenue calculations
- ⚡ **High Performance Caching** - Redis integration for optimized data retrieval
- ✅ **Input Validation** - Comprehensive data validation across all endpoints
- 🛡️ **Global Exception Handling** - Consistent error responses
- 🧪 **Comprehensive Testing** - Unit and integration test coverage
- 📚 **API Documentation** - Interactive Swagger/OpenAPI specification
- 🐳 **Docker Ready** - Pre-configured containerization

---

## 🏗️ Tech Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Java** | 17+ | Core language |
| **Spring Boot** | 3.5.10 | Framework |
| **Spring Security** | Latest | Authentication & Authorization |
| **JWT** | Latest | Token management |
| **Spring Data JPA** | Latest | ORM & Data access |
| **Spring Cache** | Latest | Caching abstraction |
| **Redis** | 8.4.1 | Distributed cache store |
| **Oracle Database** | XE 21c | Primary database |
| **Flyway** | Latest | Database migrations |
| **Docker** | Latest | Containerization |
| **Swagger/OpenAPI** | 3.0 | API documentation |

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

### Option 1: Docker & Docker Compose (Recommended)
- **Docker** (version 20.10+)
- **Docker Compose** (version 1.29+)

### Option 2: Local Development
- **Java Development Kit (JDK)** version 17 or higher
- **Maven** version 3.8+
- **Oracle Database XE** (21c) running locally
- **Redis** server running locally
- **Git** for version control

---

## 🚀 Quick Start

### Using Docker (Recommended)

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Vitor-C-Souza/prodmanager-API.git
   cd prodmanager
   ```

2. **Start all services:**
   ```bash
   docker-compose up -d
   ```

3. **Access the API:**
   - API Base: `http://localhost:8080/api/v1`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

4. **Create your first user:**
   ```bash
   curl -X POST http://localhost:8080/api/v1/auth/register \
     -H "Content-Type: application/json" \
     -d '{"email":"user@example.com","password":"securePassword123","role":"ADMIN"}'
   ```

### Running Locally

1. **Clone and navigate:**
   ```bash
   git clone https://github.com/Vitor-C-Souza/prodmanager-API.git
   cd prodmanager
   ```

2. **Configure environment variables:**
   ```bash
   export SPRING_PROFILES_ACTIVE=dev
   export SPRING_DATASOURCE_URL=jdbc:oracle:thin:@localhost:1521/XEPDB1
   export SPRING_DATASOURCE_USERNAME=produser
   export SPRING_DATASOURCE_PASSWORD=prodpass
   export SPRING_DATA_REDIS_HOST=localhost
   export SPRING_DATA_REDIS_PORT=6379
   ```

3. **Build the project:**
   ```bash
   mvn clean package
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

5. **Access Swagger UI:**
   - Open `http://localhost:8080/swagger-ui.html` in your browser

---

## 🔐 Authentication

ProdManager uses **JWT (JSON Web Token)** for secure API access.

### Register a New User

**Endpoint:** `POST /auth/register`

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "StrongPassword123!",
    "role": "ADMIN"
  }'
```

```json
{
  "message": "User registered successfully"
}
```

### Login

**Endpoint:** `POST /auth/login`

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "StrongPassword123!"
  }'
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNjM5NTk3MjAwfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```

### Using the Token

Include the token in the `Authorization` header for all protected endpoints:

```bash
curl -X GET http://localhost:8080/api/v1/products \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

## 👥 User Roles & Permissions

| Role | Description | Permissions |
|------|-------------|-------------|
| **ADMIN** | Full system access | Create, read, update, delete all resources |
| **USER** | Limited access | Read-only access to most endpoints |

**Important:** Most write operations (`POST`, `PUT`, `DELETE`) require `ADMIN` role.



---

## 📡 API Endpoints

### Products Management

**Base path:** `/api/v1/products`

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|-----------------|
| `POST` | `/products` | Create new product | ✅ ADMIN |
| `GET` | `/products` | List all products | ✅ Any |
| `GET` | `/products/{id}` | Get product details | ✅ Any |
| `PUT` | `/products/{id}` | Update product | ✅ ADMIN |
| `DELETE` | `/products/{id}` | Delete product | ✅ ADMIN |

**Example - Create Product:**
```bash
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{
    "name": "Smartphone X1",
    "description": "Latest model",
    "unitPrice": 999.99
  }'
```

### Raw Materials Management

**Base path:** `/api/v1/raw-materials`

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|-----------------|
| `POST` | `/raw-materials` | Create raw material | ✅ ADMIN |
| `GET` | `/raw-materials` | List all materials | ✅ Any |
| `GET` | `/raw-materials/{id}` | Get material details | ✅ Any |
| `PUT` | `/raw-materials/{id}` | Update material | ✅ ADMIN |
| `DELETE` | `/raw-materials/{id}` | Delete material | ✅ ADMIN |
| `PUT` | `/raw-materials/{id}/stock` | Update stock level | ✅ ADMIN |

**Example - Update Stock:**
```bash
curl -X PUT http://localhost:8080/api/v1/raw-materials/{id}/stock \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{
    "quantity": 500
  }'
```

### Product-Material Relationship

**Base path:** `/api/v1/products/{productId}`

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|-----------------|
| `POST` | `/products/{productId}/materials` | Associate material to product | ✅ ADMIN |

**Example - Link Material to Product:**
```bash
curl -X POST http://localhost:8080/api/v1/products/1/materials \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{
    "materialId": 5,
    "quantityRequired": 10
  }'
```

### Production Management

**Base path:** `/api/v1/production`

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|-----------------|
| `GET` | `/production/simulate` | Simulate production plan | ✅ Any |
| `GET` | `/production/report` | Get financial report | ✅ Any |

**Example - Run Production Simulation:**
```bash
curl -X GET http://localhost:8080/api/v1/production/simulate \
  -H "Authorization: Bearer <TOKEN>"
```

**Response:**
```json
{
  "plan": [
    {
      "productId": 1,
      "quantity": 50,
      "revenue": 49999.50
    }
  ],
  "totalRevenue": 49999.50,
  "timestamp": "2024-02-10T15:30:00Z"
}
```

---

## ⚙️ Production Simulation Algorithm

The intelligent **production simulation engine** is the core of ProdManager. It optimizes manufacturing decisions based on available inventory and profit margins.

### How It Works

1. **Inventory Assessment** - Scans current stock levels of all raw materials
2. **Demand Calculation** - Determines how many units of each product can be produced
3. **Profit Optimization** - Prioritizes products by higher unit profit margin
4. **Material Deduction** - Simulates consuming raw materials for production
5. **Report Generation** - Produces detailed financial and production reports

### Key Algorithmic Features

- 📈 **Profit-Driven Prioritization** - Products with higher margins are produced first
- 🔄 **Inventory-Constrained** - Production respects available raw materials
- 💰 **Revenue Calculation** - Accurate financial forecasting
- ⚡ **Real-time Execution** - Immediate results without delays

### Example Output

```json
{
  "production": [
    {
      "productId": 1,
      "productName": "Premium Smartphone",
      "unitsProduced": 100,
      "unitPrice": 999.99,
      "totalRevenue": 99999.00
    },
    {
      "productId": 2,
      "productName": "Budget Tablet",
      "unitsProduced": 250,
      "unitPrice": 299.99,
      "totalRevenue": 74997.50
    }
  ],
  "totalRevenue": 174996.50,
  "executedAt": "2024-02-10T15:35:42Z"
}
```

---

## 📦 Running with Docker (Recommended)

### Benefits of Docker Deployment
- ✅ Consistent environment across machines
- ✅ No local database setup required
- ✅ Redis cache pre-configured
- ✅ Easy scaling and deployment
- ✅ Isolated services

### Docker Compose Setup

The `docker-compose.yml` includes:
- **Oracle Database XE** (port 1521)
- **Redis** (port 6379)
- **ProdManager API** (port 8080)
- Pre-configured networking

### Commands

**Start the application:**
```bash
docker-compose up -d
```

**View logs:**
```bash
docker-compose logs -f api
```

**Stop services:**
```bash
docker-compose down
```

**Remove all data and images:**
```bash
docker-compose down -v
```

---

## 🖥️ Running Locally

### Step-by-Step Guide

1. **Ensure Java 17+ is installed:**
   ```bash
   java -version
   ```

2. **Set environment variables:**
   ```bash
   export SPRING_PROFILES_ACTIVE=dev
   export SPRING_DATASOURCE_URL=jdbc:oracle:thin:@localhost:1521/XEPDB1
   export SPRING_DATASOURCE_USERNAME=produser
   export SPRING_DATASOURCE_PASSWORD=prodpass
   export SPRING_DATA_REDIS_HOST=localhost
   export SPRING_DATA_REDIS_PORT=6379
   ```

3. **Run Maven clean build:**
   ```bash
   ./mvnw clean package
   ```

4. **Start the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Verify startup:**
   ```bash
   curl http://localhost:8080/actuator/health
   ```

Expected response:
```json
{
  "status": "UP"
}
```

---

## 📚 API Documentation (Swagger/OpenAPI)

Interactive API documentation is automatically generated using **Swagger UI**.

### Access Swagger UI

```
http://localhost:8080/swagger-ui.html
```

### Swagger JSON Spec

```
http://localhost:8080/v3/api-docs
```

### Features

- 🔍 Browse all endpoints
- 🧪 Test endpoints directly
- 📖 View request/response schemas
- 📌 See all response codes and examples
- 🔐 Authorize with JWT token

---

## 🗄️ Database Migrations

ProdManager uses **Flyway** for version-controlled database schema management.

### How Migrations Work

1. Migrations run **automatically** on application startup
2. Database schema is **always in sync** with code
3. Version control **tracks all changes**
4. **Rollback support** for failed migrations

### Migration Files Location

```
src/main/resources/db/migration/
```

### Migration Naming Convention

```
V{VERSION}__{DESCRIPTION}.sql
```

**Example:**
- `V1__Initial_Schema.sql`
- `V2__Add_Production_Tables.sql`
- `V3__Add_Indexes.sql`

### Viewing Applied Migrations

All migrations are tracked in the `flyway_schema_history` table:

```sql
SELECT * FROM flyway_schema_history;
```

---

## 📂 Project Structure

```
prodmanager/
├── src/
│   ├── main/
│   │   ├── java/teste/autoflex/vitorcsouza/prodmanager/
│   │   │   ├── controller/          # REST API endpoints
│   │   │   ├── service/             # Business logic layer
│   │   │   ├── repository/          # Data access layer (JPA)
│   │   │   ├── model/               # JPA entity classes
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── util/                # Utility classes
│   │   │   ├── infra/               # Infrastructure & config
│   │   │   │   ├── security/        # JWT, authentication
│   │   │   │   ├── exception/       # Global exception handling
│   │   │   │   └── config/          # Application configuration
│   │   │   └── ProdManagerApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── db/migration/        # Flyway migrations
│   │       └── ...
│   └── test/
│       ├── java/                    # Unit & integration tests
│       └── resources/
├── docker-compose.yml
├── Dockerfile
├── pom.xml
├── mvnw & mvnw.cmd
└── README.md
```

### Layer Responsibilities

| Layer | Purpose | Example |
|-------|---------|---------|
| **Controller** | HTTP request handling | `ProductController.java` |
| **Service** | Business logic & rules | `ProductService.java` |
| **Repository** | Database operations | `ProductRepository.java` |
| **Model** | Database entities | `Product.java` |
| **DTO** | Request/response objects | `ProductDTO.java` |
| **Infra** | Cross-cutting concerns | Security, exception handling |

---

## 🧪 Testing

### Running Tests

**Run all tests:**
```bash
./mvnw test
```

**Run tests with coverage:**
```bash
./mvnw test jacoco:report
```

**Run specific test class:**
```bash
./mvnw test -Dtest=ProductControllerTest
```

### Test Structure

```
src/test/java/
├── controller/          # Controller unit tests
├── service/impl/        # Service integration tests
└── resources/           # Test data & properties
```

### Test Coverage

The project includes tests for:
- ✅ REST Controllers (MockMvc)
- ✅ Service Layer (Business logic)
- ✅ Repository Layer (Database queries)
- ✅ Authentication & Authorization
- ✅ Exception handling

### Example Test

```java
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testCreateProduct() throws Exception {
        mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + token)
            .content(json))
            .andExpect(status().isCreated());
    }
}
```

---

## 🔧 Configuration

### Environment Variables

Create a `.env` file or set these variables:

```env
# Database Configuration
SPRING_DATASOURCE_URL=jdbc:oracle:thin:@localhost:1521/XEPDB1
SPRING_DATASOURCE_USERNAME=produser
SPRING_DATASOURCE_PASSWORD=prodpass
SPRING_DATASOURCE_DRIVER_CLASS_NAME=oracle.jdbc.driver.OracleDriver

# Redis Configuration
SPRING_DATA_REDIS_HOST=localhost
SPRING_DATA_REDIS_PORT=6379

# JPA/Hibernate
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
SPRING_JPA_SHOW_SQL=false
SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.OracleDialect

# JWT Configuration
JWT_SECRET=your-secret-key-min-32-characters-long
JWT_EXPIRATION=86400000

# Server
SERVER_PORT=8080
SERVER_SERVLET_CONTEXT_PATH=/
```

### Application Properties

Main configuration file: `src/main/resources/application.properties`

### Profiles

- **dev** - Development environment (local database)
- **prod** - Production environment (optimized settings)

---

## 🐛 Troubleshooting

### Common Issues & Solutions

#### 1. Database Connection Error
```
Error: Unable to connect to Oracle Database
```

**Solution:**
```bash
# Verify Oracle is running
docker ps | grep oracle

# Check database credentials
docker-compose logs db

# Restart services
docker-compose restart db
```

#### 2. Port Already in Use
```
Error: Port 8080 already in use
```

**Solution:**
```bash
# Change port in application.properties or docker-compose.yml
SERVER_PORT=8081

# Or kill the process using port 8080
lsof -i :8080 | grep LISTEN
kill -9 <PID>
```

#### 3. JWT Token Invalid
```
Error: 401 Unauthorized
```

**Solution:**
- Ensure token is included in Authorization header
- Format: `Authorization: Bearer <token>`
- Check token expiration time
- Verify JWT secret matches configuration

#### 4. Maven Build Fails
```
[ERROR] COMPILATION ERROR
```

**Solution:**
```bash
# Clean and rebuild
./mvnw clean install

# Skip tests if needed
./mvnw clean package -DskipTests

# Update dependencies
./mvnw dependency:resolve
```

#### 5. Docker Image Build Error
```
Error: Docker build failed
```

**Solution:**
```bash
# Rebuild without cache
docker-compose build --no-cache

# Remove dangling images
docker image prune -a

# Check Docker is running
docker ps
```

---

## 📋 Environment Setup Checklist

- [ ] Clone repository
- [ ] Install Java 17+ or Docker
- [ ] Configure database credentials
- [ ] Set environment variables
- [ ] Run `mvn clean package` (local) or `docker-compose up` (Docker)
- [ ] Verify API is running: `curl http://localhost:8080/actuator/health`
- [ ] Access Swagger: `http://localhost:8080/swagger-ui.html`
- [ ] Create user account via `/auth/register`
- [ ] Test endpoints with your token

---

## 🚀 Performance Tips

### Optimize Database Queries
- Enable query caching for frequently accessed data
- Use pagination for large result sets
- Index frequently queried columns

### Application Optimization
```properties
# Connection pooling
SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE=20
SPRING_DATASOURCE_HIKARI_MINIMUM_IDLE=5

# Cache configuration
SPRING_CACHE_TYPE=redis
```

### Monitor Performance
```bash
# View metrics
curl http://localhost:8080/actuator/metrics

# Health check
curl http://localhost:8080/actuator/health/db
```

---

## 🔒 Security Best Practices

1. **Never commit sensitive data** - Use environment variables for credentials
2. **Change default passwords** - Update database and JWT secrets
3. **Use HTTPS in production** - Implement SSL/TLS certificates
4. **Implement API rate limiting** - Prevent brute force attacks
5. **Regular security audits** - Review dependencies for vulnerabilities
6. **Validate all inputs** - Use Spring Validation annotations
7. **Log security events** - Monitor authentication attempts

### Running Security Audit

```bash
mvn dependency-check:check
```

---

## 🔄 Development Workflow

### Branching Strategy

```
master (production)
  ├── develop (staging)
  │   ├── feature/user-auth
  │   ├── feature/product-api
  │   └── fix/bug-123
  └── hotfix/critical-issue
```

### Creating a Feature Branch

```bash
git checkout -b feature/feature-name
# Make changes
git commit -m "Add feature description"
git push origin feature/feature-name
```

### Building Before Commit

```bash
./mvnw clean package
./mvnw test
./mvnw spotbugs:check
```

---

## 📈 Future Enhancements & Roadmap

### Planned Features

- **📊 Advanced Analytics Dashboard** - Real-time production metrics
- **💾 Redis Caching Layer** - Improved performance for frequently accessed data
- **📱 Mobile Application** - Companion mobile app for on-the-go management
- **☁️ Cloud Deployment** - AWS, Azure, and GCP deployment guides
- **📊 Monitoring & Observability** - Prometheus + Grafana integration
- **🔔 Notifications System** - Email/SMS alerts for critical events
- **🌐 Multi-language Support** - i18n implementation
- **📦 Supplier Integration API** - Connect with external suppliers
- **🤖 AI-Powered Forecasting** - Machine learning for demand prediction

### Contributions Welcome! 🎉

Interested in contributing? Please see [Contributing Guidelines](#contributing).

---

## 📄 License

This project is licensed under the **MIT License** - see the LICENSE file for details.

---

## 👨‍💻 Author & Contact

**Vítor Cavalcante Souza**  
Java / Backend Developer | Spring Boot Specialist

### Connect With Me

- 🔗 **LinkedIn:** [linkedin.com/in/vitorcavalcantesouza](https://www.linkedin.com/in/vitorcavalcantesouza)
- 🐙 **GitHub:** [@Vitor-C-Souza](https://github.com/Vitor-C-Souza)
- 📧 **Email:** vitorcsouza@example.com
- 🌐 **Portfolio:** [your-portfolio.com](https://your-portfolio.com)

---

## 📚 Additional Resources

### Documentation
- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [Oracle Database Documentation](https://docs.oracle.com/en/database/)
- [Flyway Documentation](https://flywaydb.org/documentation/)

### Related Articles & Guides
- [JWT Best Practices](https://tools.ietf.org/html/rfc8725)
- [REST API Design Guidelines](https://restfulapi.net/)
- [Spring Boot Production Checklist](https://spring.io/blog/2016/07/04/spring-boot-spring-security-saml-sso-tutorial)

### Tools & Libraries Used
- [Swagger UI](https://swagger.io/tools/swagger-ui/)
- [Apache Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)

---

## ❓ FAQ

**Q: Can I use a different database?**  
A: Yes, modify `pom.xml` and `application.properties`. ProdManager supports PostgreSQL, MySQL, SQL Server, etc.

**Q: How do I reset the database?**  
A: For Docker: `docker-compose down -v`. For local: Drop and recreate the schema.

**Q: Is this production-ready?**  
A: This is a reference implementation. For production, add monitoring, rate limiting, and audit logs.

**Q: How do I add new endpoints?**  
A: Create a new controller class, define endpoints, implement service logic, and add tests.

**Q: What's the default admin user?**  
A: There is none. You must create users via the `/auth/register` endpoint.

---

## 🙏 Acknowledgments

- Spring Boot community for excellent framework
- Oracle for database technology
- All contributors and users of ProdManager

---

**⭐ If this project helped you, please consider giving it a star on GitHub!**

Last updated: February 2026
