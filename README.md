# Employee REST API

A Spring Boot REST API for managing employee records, backed by a MySQL database using Spring Data JPA.

---

## Table of Contents

- [Tech Stack](#tech-stack)
- [Postman Collection](#postman-collection)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Environment Variables](#environment-variables)
- [Getting Started](#getting-started)
- [API Reference](#api-reference)
- [Response Format](#response-format)

---

## Tech Stack

| Layer        | Technology                              |
|--------------|-----------------------------------------|
| Language     | Java 21                                 |
| Framework    | Spring Boot 4.1.0                       |
| Persistence  | Spring Data JPA + Hibernate             |
| Database     | MySQL                                   |
| Build Tool   | Maven (via Maven Wrapper)               |
| Utilities    | Lombok                                  |

---

## Postman Collection

An interactive API collection is available on Postman for easy testing of all endpoints:

🔗 [View Postman Collection](https://documenter.getpostman.com/view/30170318/2sBXwtrASn)

---

## Project Structure

```
restapi/
├── src/
│   └── main/
│       ├── java/com/employee/restapi/
│       │   ├── RestapiApplication.java          # Application entry point
│       │   ├── controller/
│       │   │   └── main.java                    # REST controller (all endpoints)
│       │   ├── entity/
│       │   │   └── EmployeeEntity.java          # JPA entity mapped to "Employee" table
│       │   ├── repository/
│       │   │   └── EmployeeRepo.java            # JpaRepository with custom query
│       │   ├── responseHandler/
│       │   │   └── Response.java                # Standardized response builder
│       │   └── service/
│       │       ├── EmployeeService.java         # Service interface
│       │       └── impl/
│       │           └── EmployeeServiceImpl.java # Service implementation
│       └── resources/
│           └── application.properties           # App configuration (env-var driven)
└── pom.xml
```

---

## Prerequisites

- Java 21+
- Maven 3.x (or use the included `mvnw` / `mvnw.cmd` wrapper)
- A running MySQL instance with an `Employee` table

### MySQL Table Schema

```sql
CREATE TABLE Employee (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstName  VARCHAR(255),
    middleName VARCHAR(255),
    lastName   VARCHAR(255),
    department VARCHAR(255)
);
```

---

## Environment Variables

The application is fully configured via environment variables. Set the following before running:

| Variable          | Description                          | Example                                          |
|-------------------|--------------------------------------|--------------------------------------------------|
| `EMP_SERVER_PORT` | Port the server listens on           | `8080`                                           |
| `EMP_DB_URL`      | JDBC connection URL for MySQL        | `jdbc:mysql://localhost:3306/employeedb`         |
| `EMP_DB_USERNAME` | MySQL username                       | `root`                                           |
| `EMP_DB_PASSWORD` | MySQL password                       | `secret`                                         |

---

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd restapi
```

### 2. Set environment variables

**Windows (PowerShell):**
```powershell
$env:EMP_SERVER_PORT = "8080"
$env:EMP_DB_URL      = "jdbc:mysql://localhost:3306/employeedb"
$env:EMP_DB_USERNAME = "root"
$env:EMP_DB_PASSWORD = "secret"
```

**Linux / macOS:**
```bash
export EMP_SERVER_PORT=8080
export EMP_DB_URL=jdbc:mysql://localhost:3306/employeedb
export EMP_DB_USERNAME=root
export EMP_DB_PASSWORD=secret
```

### 3. Run the application

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

The API will be available at `http://localhost:<EMP_SERVER_PORT>/api/v1.0.0/employee`.

---

## API Reference

**Base URL:** `/api/v1.0.0/employee`

### Get All Employees

Retrieves a paginated, sorted list of employees.

```
GET /get/
```

**Query Parameters:**

| Parameter | Type   | Default | Description                           |
|-----------|--------|---------|---------------------------------------|
| `page`    | int    | `0`     | Zero-based page index                 |
| `size`    | int    | `5`     | Number of records per page            |
| `sortBy`  | string | `id`    | Field to sort by (ascending)          |

**Example:**
```bash
curl "http://localhost:8080/api/v1.0.0/employee/get/?page=0&size=10&sortBy=lastName"
```

---

### Create Employee

Creates a new employee record.

```
POST /create/
```

**Request Body (JSON):**
```json
{
  "firstName":  "Jane",
  "middleName": "A",
  "lastName":   "Doe",
  "department": "Engineering"
}
```

**Example:**
```bash
curl -X POST "http://localhost:8080/api/v1.0.0/employee/create/" \
     -H "Content-Type: application/json" \
     -d '{"firstName":"Jane","middleName":"A","lastName":"Doe","department":"Engineering"}'
```

---

### Update Employee

Updates an existing employee by ID.

```
PUT /update/{id}
```

**Path Parameter:** `id` — the employee's numeric ID.

**Request Body (JSON):**
```json
{
  "firstName":  "Jane",
  "middleName": "B",
  "lastName":   "Doe",
  "department": "HR"
}
```

**Example:**
```bash
curl -X PUT "http://localhost:8080/api/v1.0.0/employee/update/1" \
     -H "Content-Type: application/json" \
     -d '{"firstName":"Jane","middleName":"B","lastName":"Doe","department":"HR"}'
```

---

### Delete Employee

Deletes an employee by ID.

```
DELETE /delete/{id}
```

**Path Parameter:** `id` — the employee's numeric ID.

**Example:**
```bash
curl -X DELETE "http://localhost:8080/api/v1.0.0/employee/delete/1"
```

---

## Response Format

All endpoints return a consistent JSON envelope:

```json
{
  "responseMessage": "Success",
  "responseStatus":  200,
  "timestamp":       "2026-06-18 08:00:00.000",
  "data":            { ... }
}
```

| Field             | Type             | Description                            |
|-------------------|------------------|----------------------------------------|
| `responseMessage` | string           | `"Success"` or `"ERROR"`               |
| `responseStatus`  | int              | HTTP status code                       |
| `timestamp`       | string           | Server timestamp of the response       |
| `data`            | object or null   | Payload; `null` on error               |

### Error Responses

| Scenario                        | HTTP Status |
|---------------------------------|-------------|
| Invalid input / constraint fail | `400 Bad Request` |
| Unexpected server error         | `500 Internal Server Error` |
