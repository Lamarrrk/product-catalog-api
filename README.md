# Product Catalog REST API

This is a RESTful API for a product catalog management system built with Spring Boot. It supports products with a dynamic number of attributes (from a few to 200+) using JSON database columns.

## Technology Stack
* Java 21+
* Spring Boot 3.x
* Spring Data JPA
* Liquibase
* H2 Database (In-Memory)
* Maven

## Setup & Running Instructions
1. Clone the repository.
2. No external database installation is required (H2 runs in-memory).
3. The database schema and initial test data (producers) are automatically populated via Liquibase upon application startup.
4. Go to the `demo` directory.
5. Run the application using Maven:
   ```bash
   mvn spring-boot:run
