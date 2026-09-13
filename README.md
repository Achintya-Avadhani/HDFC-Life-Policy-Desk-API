# HDFC Life Policy Desk API

A Spring Boot application for managing HDFC Life policy desk operations.

## How to Run

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Or using Maven:

```bash
mvn spring-boot:run
```

The application uses the `dev` profile by default.

## API Endpoints

| Method | Path | Status Codes |
|---|---|---|
| GET | `/api/policies` | 200 |
| GET | `/api/policies/{policyNo}` | 200, 404 |
| GET | `/api/policies?status=Active` | 200 |
| GET | `/api/policies?type=TERM` | 200 |
| POST | `/api/policies` | 201, 409 |
| PUT | `/api/policies/{policyNo}` | 200, 404 |
| DELETE | `/api/policies/{policyNo}` | 204, 404 |
| GET | `/api/policies/{policyNo}/claims` | 200, 404 |
| POST | `/api/claims` | 201, 400, 404 |
| GET | `/api/claims/{claimNo}` | 200, 404 |

## Entity Relationships

The application contains five database tables:

- `customers` — stores customer information.
- `policies` — stores policy information and references `customers(id)`.
- `claims` — stores claim information and references `policies(id)`.
- `riders` — stores available policy riders.
- `policy_riders` — joins policies and riders using foreign keys.

Foreign key relationships:

- `policies.customer_id` → `customers(id)`
- `claims.policy_id` → `policies(id)`
- `policy_riders.policy_id` → `policies(id)`
- `policy_riders.rider_id` → `riders(id)`

## In-Memory Store vs PostgreSQL

An in-memory store is useful for development, testing, and small applications where data does not need to survive application restarts. PostgreSQL is better when persistent data, multiple users, transactions, and production-scale storage are required. Flyway provides version-controlled and repeatable database migrations. Unlike `ddl-auto=update`, Flyway gives explicit control over database schema changes through SQL migration files. This project keeps REST policy data in memory while Flyway manages the PostgreSQL-ready database schema.

## Swagger

Swagger UI is available at:

http://localhost:8080/swagger-ui/index.html

OpenAPI documentation is available at:

http://localhost:8080/v3/api-docs

Swagger UI provides interactive documentation and allows the API endpoints to be tested from the browser.

## Technologies

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Validation
- Maven
- Flyway
- H2 Database
- PostgreSQL
- Springdoc OpenAPI / Swagger
- Lombok
