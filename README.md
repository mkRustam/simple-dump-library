# Spring Library App
A server-rendered web application for browsing, borrowing, and reviewing books, built with **Spring Boot 3**, **Thymeleaf**, and **Spring Security**.
## Tech Stack
| Layer | Technology |
|-------|------------|
| Language | Java 21 |
| Framework | Spring Boot 3.5 |
| View | Thymeleaf + Layout Dialect |
| Security | Spring Security 6 (form login, method-level authorization) |
| Persistence | Spring Data JPA / Hibernate |
| Database | PostgreSQL 17 |
| Build | Gradle |

## Screenshots
<img width="228" height="260" alt="Book card" src="https://github.com/user-attachments/assets/4959655e-cbc8-4453-b2fc-58d1602a75d0" />
<img width="228" height="260" alt="Book card" src="https://github.com/user-attachments/assets/b6113999-24fa-4722-8a1a-b32a586dff0b" />
<img width="228" height="260" alt="Book card" src="https://github.com/user-attachments/assets/2c482948-e81e-4237-9b0e-6b7cbadfcd18" />
<img width="612" height="240" alt="Book catalog" src="https://github.com/user-attachments/assets/e09846d2-367e-4d8d-99ff-5ccbd6f45511" />
<img width="609" height="145" alt="My books" src="https://github.com/user-attachments/assets/50eddb74-ee96-4296-917b-1a789c5168e6" />
<img width="622" height="558" alt="Book detail" src="https://github.com/user-attachments/assets/589824ca-2ca7-44f6-ab41-a9fd744e4b1d" />
<img width="622" height="558" alt="Book reviews" src="https://github.com/user-attachments/assets/06b1af80-6074-4e57-a765-0a0ac032d32f" />
<img width="622" height="558" alt="Book reviews" src="https://github.com/user-attachments/assets/b2113b29-bb75-44ef-b7f8-c9993f82b8cf" />
<img width="608" height="183" alt="Login page" src="https://github.com/user-attachments/assets/bf54901e-16db-4331-a557-ea59e92f0dde" />

## Features
- **Book catalog** — paginated list of available books with search by title and genre filter
- **Book loans** — authenticated users can borrow ("hold") a book and return it from their personal page
- **Reviews & ratings** — users leave a text review with a 1–5 star rating (one review per user per book); average rating is displayed on book cards and the detail page
- **Role-based access** — four roles with granular authorities:
| Role | Authorities |
|------|-------------|
| `ROLE_ADMIN` | `BOOK_READ`, `BOOK_MANAGE`, `USER_READ`, `USER_MANAGE`, `LOAN_MANAGE`, `REVIEW_MANAGE`, `ADMIN_PANEL_ACCESS` |
| `ROLE_LIBRARIAN` | `BOOK_READ`, `BOOK_MANAGE`, `LOAN_MANAGE`, `REVIEW_MANAGE` |
| `ROLE_USER` | `BOOK_READ`, `LOAN_BASIC`, `REVIEW_CREATE` |
| `ROLE_GUEST` | `BOOK_READ` |
- **Sample data** — on startup the app seeds users, books (from `data/books.json`), and sample reviews so you can explore immediately
## Project Structure
```
src/main/java/com/mkr/springappsecurity/
├── auth/          # Security config, User/Role/Authority entities, UserDetailsManager
├── book/          # Book entity, controller, service, repository, DTO
├── person/        # Person entity, controller, service
├── genre/         # Genre entity, service
├── review/        # Review entity, controller, service, DTO
├── init/          # CommandLineRunners for seeding data
└── common/        # Global exception handler
src/main/resources/
├── application.yml
├── data/books.json
├── static/css/
└── templates/
    ├── layout.html
    ├── public/     # Login page
    └── private/    # Person page, library pages (list, detail, reviews)
```

## Getting Started
### Prerequisites
- **Java 21+**
- **Docker** (for the database) or a local PostgreSQL instance
### 1. Start the database
```bash
docker compose up -d
```
This starts PostgreSQL 17 on port **5432** with database `spring_app_security_db` and credentials `admin` / `admin`.
### 2. Run the application
```bash
./gradlew bootRun
```
The app will be available at **http://localhost:8080**.
Hibernate automatically creates/updates the schema, and `CommandLineRunner` beans seed initial data.
### 3. Log in
Two users are created on startup:
| Username | Password | Role |
|----------|----------|------|
| `user1` | `password1` | `ROLE_USER` |
| `admin1` | `admin1` | `ROLE_ADMIN` |
## Configuration

Key settings in `application.yml` (overridable via environment variables):
| Variable | Default | Description |
|----------|---------|-------------|
| `DB_URL` | `jdbc:postgresql://localhost:5432/spring_app_security_db` | JDBC connection URL |
| `DB_USER` | `admin` | Database username |
| `DB_PASSWORD` | `admin` | Database password |
| `app.security.storage-type` | `database` | `database` for JPA-backed users, `in_memory` for hardcoded users |
