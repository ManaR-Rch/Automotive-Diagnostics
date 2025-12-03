# Automotive Diagnostics Spring Boot Project

A Spring Boot application for automotive diagnostics with REST API endpoints.

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/automotive/
│   │       ├── AutomotiveDiagnosticsApplication.java (main entry point)
│   │       ├── controller/ (REST controllers)
│   │       ├── service/ (business logic)
│   │       ├── model/ (entity models)
│   │       └── repository/ (data access)
│   └── resources/
│       └── application.properties (configuration)
└── test/

pom.xml (Maven configuration)
```

## Prerequisites

- Java 17 or later
- Maven 3.8.0 or later

## Building the Project

### Using Maven

```bash
# Clean and build the project
mvn clean install

# Run the application
mvn spring-boot:run

# Run tests
mvn test
```

### Using IDE

Build and run directly from VS Code or your IDE with Spring Boot extension support.

## API Endpoints

- `GET /` - Welcome message
- `GET /health` - Health check endpoint

## Dependencies

- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (in-memory)
- Lombok (for code generation)

## Configuration

Configuration is managed through `src/main/resources/application.properties`.

Default settings:

- Server runs on `http://localhost:8080`
- H2 console available at `http://localhost:8080/h2-console`
"# Automotive-Diagnostics" 
