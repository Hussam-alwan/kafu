# Kafu Project

## Overview
Kafu is a Spring Boot application that implements OAuth2/OpenID Connect authentication using Keycloak, with PostgreSQL as the database backend.

## Prerequisites
- Docker

## Quick Start

### 1. Clone the Repository
```bash
   git clone https://github.com/Abdulhadi-Assi/kafu.git
```

```bash
   cd kafu
```

### 3. Start Services

Start all services using Docker Compose:

```bash
   docker compose up -d
```

The following services will be started:

- PostgreSQL (Port: 5432)
- PgAdmin4 (Port: 5050)
- Keycloak (Port: 9098)
- Kafu Application (Port: 8080)

### 4. Access Points
- Application: http://localhost:8080
- Keycloak Admin Console: http://localhost:9098
- keycloack registration page: http://localhost:9098/realms/kafu-realm/account
- PgAdmin4: http://localhost:5050
- Swagger UI: http://localhost:8080/swagger-ui/index.html

## Service Credentials
### PostgreSQL

Database:kafu

Username:user

Password:password

### PgAdmin4

Password: admin

host name:postgresql

### Keycloak


Admin Username: admin

Admin Password: admin

Realm: kafu-realm

Client-ID:kafu-client

## API Documentation
The API is documented using Swagger UI and includes:

- OAuth2 authentication integration
- Available endpoints documentation
- Request/response schemas
- Built-in API testing capability

## Security Features
- OAuth2/OpenID Connect with Keycloak
- JWT token validation
- Role-based access control (RBAC)



## Troubleshooting
### Common Issues
1. Services Won't Start
   
   - Check if ports 5432, 5050, 9098, or 8080 are already in use
   - Ensure Docker daemon is running
   - Check Docker logs: docker compose logs
2. Database Connection Issues
   
   - Verify PostgreSQL container is running: docker ps
   - Check database credentials in application.yml
   - Ensure database 'kafu' exists
3. Keycloak Issues
   
   - Verify Keycloak is running: http://localhost:9098
   - Check realm configuration in keycloak/imports
   - Ensure client configuration matches application.yml settings

### Viewing Logs

All services

```bash
   docker compose logs
```

Specific service

```bash
   docker compose logs [ service-name ]
```
