# TODO Application API

A RESTful Web Service built with Spring Boot that manages Categories, TODO Items, and User authentication.

---

## Features & Design Decisions

* **RESTful Architecture:** Clear separation of resources utilizing standard HTTP methods (`GET`, `POST`, `PUT`, `DELETE`).
* **Dynamic Path Parameters:** Resource identification via `{categoryId}` and `{itemId}`.
* **Resource Relationship:** Direct parent-child association where Items belong to specific Categories; `User` and `UserProfile` are mapped 1:1.
* **Unified JSON Payload:** Standardized JSON formatting across request bodies and responses.
* **Layered Architecture:**
    * **Controller Layer:** Handles HTTP endpoints and request routing.
    * **Service Layer:** Encapsulates business logic.
    * **Repository Layer:** Manages database access using Spring Data JPA.
* **Security:** Stateless JWT authentication; all endpoints require a valid token except registration, login, and category/item read/write routes explicitly opened for testing.

---

## Feature Addition: User Registration & Security

### Design Decisions

* **1:1 Entity Mapping:** `User` (`userName`, `emailAddress`, `password`) mapped one-to-one with `UserProfile` (`firstName`, `lastName`, `profileDescription`).
* **Open Registration Endpoint:** `/auth/users/register` is publicly accessible; every other endpoint requires authentication.
* **Stateless JWT Security:** No server-side session; a `JwtRequestFilter` validates the `Authorization: Bearer <token>` header on each request.
* **Password Encryption:** Passwords are hashed with `BCryptPasswordEncoder` before being persisted — plain text is never stored.

### Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/auth/users/register` | Create a new user + profile (open, no auth required) |
| `POST` | `/auth/users/login` | Authenticate a user and receive a JWT |

**Security rules:** `anyRequest().authenticated()` applies to everything except the routes below, which are `permitAll()`:
- `/auth/users`
- `/auth/users/`
- `/auth/users/login`
- `/auth/users/register`
- `/api/users/**`

### Example Request

**`POST /auth/users/register`**

```json
{
  "userName": "testuser1",
  "emailAddress": "testuser1@example.com",
  "password": "Test1234",
  "firstName": "Ahmed",
  "lastName": "Ali",
  "profileDescription": "Test user profile"
}