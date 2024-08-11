# Book Management API

This is a RESTful API built using Java Spring Boot. The API allows users to manage books and their categories. It includes features for adding, updating, deleting, and retrieving books and categories, as well as searching and filtering books based on various criteria.

## Features

- **Book Management**: Add, update, delete, and retrieve books.
- **Category Management**: Add, update, delete, and retrieve book categories.
- **Search and Filtering**: Search books by title, author, and filter by category.
- **JWT Authentication**: Secure the API endpoints using JWT tokens.
- **CRUD Operations**: Full CRUD operations for both books and categories.

## Requirements

- Java 17 or later
- Maven 3.6 or later
- MySQL or any other relational database
- Postman (for testing the API endpoints)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/bookManagement.git
cd bookManagement
```

2. Set Up the Database
   Create a database in MySQL or your preferred database. Update the application.properties file with your database connection details:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/book_management_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```
3. Build and Run the Project
   Use Maven to build and run the project:
```bash
mvn clean install
mvn spring-boot:run
```

### 4. Test the API

You can use Postman or any other API testing tool to test the endpoints. Below are some example requests:

#### 4.1 Authentication

```bash
POST /api/auth/login
POST /api/auth/register
```
4.2 Book Management

```bash
GET    /api/books
GET    /api/books/{id}
POST   /api/books
PUT    /api/books/{id}
DELETE /api/books/{id}
GET    /api/books/search?title={title}&author={author}&categoryId={categoryId}
GET    /api/books/by-category/{categoryId}

```
4.3 Category Management

```bash
GET    /api/categories
POST   /api/categories
PUT    /api/categories/{id}
DELETE /api/categories/{id}

```
```bash
bookManagement/
├── src/main/java/com/example/bookmanagement/
│   ├── controller/         # Controllers for handling API requests
│   ├── dto/                # Data Transfer Objects
│   ├── entity/             # JPA Entities
│   ├── exception/          # Custom exceptions and handlers
│   ├── repository/         # JPA Repositories
│   ├── security/           # Security configuration and JWT utility classes
│   ├── service/            # Business logic and service layer
│   └── BookManagementApplication.java # Main Spring Boot application
├── src/main/resources/
│   ├── application.properties # Application configuration
├── pom.xml                  # Maven configuration file
└── README.md                # Project documentation

```

Contributing
If you'd like to contribute, please fork the repository and use a feature branch. Pull requests are welcome.

License
This project is licensed under the MIT License.