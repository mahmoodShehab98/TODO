# Project README

## Repository

---
https://github.com/mahmoodShehab98/TODO
## API Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| **GET** | `/api/categories/` | List all categories |
| **POST** | `/api/categories/` | Create a category |
| **GET** | `/api/categories/{categoryId}` | Get category details |

---

## Design Decisions

* **RESTful Architecture:** Used standard `GET` and `POST` methods under `/api/categories` for predictable resource navigation and creation.
* **Dynamic Path Parameters:** Utilized `{categoryId}` to query specific records cleanly.
* **Unified JSON Output:** Kept all responses structured consistently to ensure easy client integration and error handling.

---
