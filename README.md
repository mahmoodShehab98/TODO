# TODO Application API

A RESTful Web Service built with Spring Boot that manages Categories and TODO Items.

---

## Features & Design Decisions

* **RESTful Architecture:** Clear separation of resources utilizing standard HTTP methods (`GET`, `POST`, `PUT`, `DELETE`).
* **Dynamic Path Parameters:** Resource identification via `{categoryId}` and `{itemId}`.
* **Resource Relationship:** Direct parent-child association where Items belong to specific Categories.
* **Unified JSON Payload:** Standardized JSON formatting across request bodies and responses.
* **Layered Architecture:**
* **Controller Layer:** Handles HTTP endpoints and request routing.
* **Service Layer:** Encapsulates business logic.
* **Repository Layer:** Manages database access using Spring Data JPA.



---

## API Endpoints

### Category Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/categories` | Retrieve all categories |
| `POST` | `/api/categories` | Create a new category |
| `GET` | `/api/category/{id}` | Retrieve a category by ID |
| `PUT` | `/api/category/{categoryId}` | Update an existing category |
| `DELETE` | `/api/categories/{categoryId}` | Delete a category |

### Item Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/categories/{categoryId}/items` | Create an item under a specific category |
| `GET` | `/api/categories/items/{itemId}` | Retrieve an item by ID |
| `GET` | `/api/categories/{categoryId}/items` | Retrieve all items under a category |
| `PUT` | `/api/categories/items/{itemId}` | Update an existing item |
| `DELETE` | `/api/categories/items/{itemId}` | Delete an item |

---

## Example Requests

### 1. Create Category

**`POST /api/categories`**

```json
{
  "name": "Work"
}

```

### 2. Create Item

**`POST /api/categories/1/items`**

```json
{
  "name": "Complete project",
  "description": "Finish the TODO application",
  "dueDate": "2026-10-01"
}

```

### 3. Update Category

**`PUT /api/category/1`**

```json
{
  "name": "Personal"
}

```

### 4. Update Item

**`PUT /api/categories/items/1`**

```json
{
  "name": "Updated project",
  "description": "Updated description",
  "dueDate": "2026-10-15"
}

```

---

## Supported CRUD Operations

* **Create:** Add new categories and todo items.
* **Read:** Fetch single entities or lists of categories/items.
* **Update:** Modify metadata for categories or items by ID.
* **Delete:** Remove categories or items from the system.