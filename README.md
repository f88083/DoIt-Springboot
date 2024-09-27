# DoIt - Task Management Application

DoIt is a robust task management application built with Spring Boot and Angular. It provides a secure and efficient way to manage tasks with user authentication.

## Purpose

The main purpose of this project is not just to implement a to-do list, but to use it as a base to explore and develop a complete Spring Boot project, along with the frontend. My goal is to implement a fully functional project, which includes user authentication, database management, logging, testing and various other technologies that are commonly required in industry projects.

## Features

- User Registration and Authentication
- JWT-based Security
- Task CRUD Operations (Create, Read, Update, Delete)
- RESTful API

## Technical Details

### Backend (Spring Boot)

The backend of DoIt is built using Spring Boot, following a layered architecture:

1. **Controller Layer**: Handles HTTP requests and responses.
   - `AuthController`: Manages user authentication and registration.
   - `TaskController`: Handles task-related operations.

2. **Service Layer**: Contains business logic.
   - `TaskService`: Implements task management logic.

3. **Repository Layer**: Interfaces with the database.
   - `UserRepository`: Manages user data persistence.
   - `TaskRepository`: Manages task data persistence.

### Security

- JWT (JSON Web Token) authentication
- Password encryption using BCrypt
- CORS configuration for secure cross-origin requests

***TODO: {Place holder for security authentication process diagram}***

### Database

- MySQL database
- JPA and Hibernate for ORM

***TODO: {Place holder for database structure diagram}***

### API Endpoints

- Authentication:
  - POST `/api/v1/auth/register`: User registration
  - POST `/api/v1/auth/login`: User login
  - POST `/api/v1/auth/logout`: User logout

- Tasks:
  - GET `/api/v1/tasks/getall`: Retrieve all tasks
  - GET `/api/v1/tasks/{taskId}`: Retrieve a specific task
  - POST `/api/v1/tasks`: Create a new task
  - PUT `/api/v1/tasks/{taskId}`: Update an existing task
  - DELETE `/api/v1/tasks/{taskId}`: Delete a task

## Setup and Configuration

1. Clone the repository
2. Configure MySQL database (use the provided `docker-compose.yml` for easy setup)
3. Update `application.properties` with your database credentials
4. Run the Spring Boot application
5. Set up and run the Angular frontend (details in [frontend repo](https://github.com/f88083/DoIt-Angular) README)

## Technologies Used

- Java 17
- Spring Boot 3.3.2
- Spring Security
- Spring Data JPA
- MySQL
- JWT for authentication
- Maven for dependency management
- Docker for database containerization

## Future Improvements

- Multiple users management
- Implement user roles and permissions
- Add task filtering and sorting capabilities
- Integrate with external calendar services
