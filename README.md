# Personal Finance Manager API

A robust Spring Boot REST API designed to help users manage their personal finances, track transactions, and monitor savings goals with strict data isolation and secure authentication.

---

##  Features
* **User Management:** Secure registration and session-based authentication.
* **Transaction Tracking:** Full CRUD (Create, Read, Update, Delete) for income and expenses.
* **Predefined Categories:** Automatic initialization of categories:
    * **INCOME:** Salary
    * **EXPENSE:** Food, Rent, Transportation, Entertainment, Healthcare, Utilities.
* **Financial Reports:** Category-wise monthly and yearly summaries.
* **Savings Goals:** Track progress toward financial targets.
* **Data Isolation:** Users can only access and manage their own financial records.

---

##  Technical Stack
* **Framework:** Spring Boot 3.x
* **Security:** Spring Security (Session-based)
* **Database:** H2 (In-memory)
* **Build Tool:** Gradle
* **Deployment:** Docker / Render

---

##  Architecture
The project follows a **Layered Architecture** to ensure maintainability and separation of concerns:
1. **Controller Layer**: Handles HTTP requests and REST endpoints.
2. **Service Layer**: Contains business logic and validation.
3. **Repository Layer**: Manages data persistence using Spring Data JPA.



---

##  How to Use (Terminal)

### 1. Register a New User
Use the following command to register a new account. This captures Username (Email), Password, Full Name, and Phone Number.

**Local Environment:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
-H "Content-Type: application/json" \
-d "{\"username\": \"user@gmail.com\", \"password\": \"password123\", \"fullName\": \"Akshit Sharma\", \"phoneNumber\": \"+911234567890\"}"
