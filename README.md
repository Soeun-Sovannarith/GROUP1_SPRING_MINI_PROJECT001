# Group 1 Spring Mini Project

## Prerequisites
- Java 21
- PostgreSQL (DB name: `spring_mini`)
- Redis
- Docker (for RustFS storage)

## Setup and Run
1. **Environment Variables**: Run following command to create your `.env` file:
   ```bash
   cp .env.example .env
   ```
   *Note: Update your DB credentials and JWT secret in the newly created `.env`.*
2. **Start Services**: Run Docker commands to start Redis and the RustFS storage:
   ```bash
   # Start Redis
   docker run -d -p 6379:6379 --name redis redis

   # Start RustFS
   docker-compose up -d
   ```
3. **Run App**: Build and run the project using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

## Documentation
Once running, you can test the APIs via Swagger UI at:
http://localhost:8080/swagger-ui/index.html
