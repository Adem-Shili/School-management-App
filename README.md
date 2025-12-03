# 🎓 School Management Application

## Objective
This project is a comprehensive **Full-Stack School Management Application** developed as a technical assessment. It is built around a secure RESTful API backend using **Spring Boot** and a single-page application (SPA) frontend built with **Angular**. The primary goal is secure administration and management of student data.

***

## 🚀 Features

### 💻 Backend (Spring Boot API)

The backend is a secure, robust API layer designed with clean architecture and modern standards.

#### Core Functionality
* **Student CRUD:** Full **C**reate, **R**ead, **U**pdate, and **D**elete operations for the `Student` entity.
* **Data Retrieval:**
    * Get all students with **Pagination**.
    * Search students by `username` or `ID`.
    * Filter students by `Level` (enums defined as needed).
* **Extra Features:**
    * **Import:** Students list via CSV file upload.
    * **Export:** Students list (CSV format).

#### Security & Standards
| Standard | Implementation |
| :--- | :--- |
| **Authentication** | **JWT-Based Authentication** for secure admin login. |
| **Authorization** | All Student APIs are **protected** and require a valid JWT token. |
| **Password Hashing** | Admin passwords are secured using **BCrypt**. |
| **Rate Limiting** | Basic protection against brute-force attacks (e.g., max 5 login attempts/minute). |
| **Input Validation** | Uses DTOs with JSR-380 annotations (`@NotBlank`, `@Size`) for backend validation. |

### 📐 Frontend (Angular UI)

* **Login Page:** Secure authentication form for Admin users.
* **Student Dashboard:** Interactive table for viewing, searching, filtering, and performing CRUD operations on student records.
* **Styling:** Utilizes **Tailwind CSS** for a responsive, utility-first design.

***

## 🛠️ Technical Stack & Architecture

### **Stack**

| Layer | Technology | Version |
| :--- | :--- | :--- |
| **Backend Framework** | **Spring Boot** | 3.x |
| **Frontend Framework** | **Angular** | 19.x |
| **Styling** | **Tailwind CSS** | Latest |
| **Authentication** | **JWT** | |
| **Database** | *[Specify your chosen database]* | |
| **Containerization** | **Docker** | (Setup recommended) |

### **Architecture**

* **Service Layer:** Implements the required `Controllers -> Services -> Repositories` structure for maintainability and clear separation of business logic.
* **DTO Pattern:** Used exclusively for request/response bodies to shield domain entities and enforce validation.
* **Global Exception Handling:** A centralized handler catches all backend exceptions, maps them to the appropriate **HTTP Status Codes** (`200`, `201`, `400`, `401`, `404`, `409`), and returns clean, standardized JSON error responses.

***

## ⚙️ How to Set Up and Run

### Prerequisites
* Java SDK (17+)
* Node.js (18+)
* npm (or yarn)

### 1. Backend Setup (`SchoolManagementApp/`)

1.  Navigate to the backend directory:
    ```bash
    cd SchoolManagementApp
    ```
2.  (Optional) Configure your database credentials in `src/main/resources/application.properties`.
3.  Run the application (using Maven Wrapper):
    ```bash
    ./mvnw spring-boot:run
    ```
    The API should be available at `http://localhost:8080`.

### 2. Frontend Setup (`SchoolManagementUI/`)

1.  Navigate to the frontend directory:
    ```bash
    cd SchoolManagementUI
    ```
2.  Install dependencies:
    ```bash
    npm install
    ```
3.  Start the development server:
    ```bash
    ng serve --open
    ```
    The Angular application should open in your browser at `http://localhost:4200`.

***


