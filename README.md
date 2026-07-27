# Robotics Club Management System

A simple web application for managing a university robotics club: members, users/roles,
equipment, equipment requests, projects, events and attendance. Built as a 3rd-year
university group mini project.

## Technologies

- Java 17
- Spring Boot 3
- Maven (with Maven Wrapper, so no local Maven install is required)
- Spring Data JPA / Hibernate
- MySQL 8
- Spring Security (form login)
- Thymeleaf
- HTML, CSS, Bootstrap 5
- Git / GitHub

## Team & Module Division

| Member | Module |
|--------|--------|
| Member 1 | Membership Management |
| Member 2 | User & Role Management |
| Member 3 | Inventory Management (Equipment) |
| Member 4 | Equipment Request Management |
| Member 5 | Project Management |
| Member 6 | Event Management |
| Member 7 | Attendance Management |

See [GROUP_HANDOVER.md](GROUP_HANDOVER.md) for full handover details and per-member tasks.

## Folder Structure

```
robotic-club-ms/
├── .gitignore
├── pom.xml
├── mvnw, mvnw.cmd, .mvn/           # Maven wrapper (no local Maven install needed)
├── README.md
├── GROUP_HANDOVER.md
│
├── database/
│   └── robotics_club.sql          # Full database schema + sample data
│
└── src/
    ├── main/
    │   ├── java/com/roboticsclub/
    │   │   ├── RoboticsClubApplication.java
    │   │   ├── controller/        # MemberController, LoginController, UserController...
    │   │   ├── service/           # MemberService, UserService...
    │   │   ├── repository/        # MemberRepository, RoleRepository, UserRepository...
    │   │   ├── model/             # Member, Role, User...
    │   │   └── config/            # SecurityConfig
    │   └── resources/
    │       ├── application.properties
    │       ├── templates/         # Thymeleaf views (login, dashboard, members/...)
    │       └── static/            # css/, js/
    └── test/
        ├── java/com/roboticsclub/MemberCrudTests.java
        └── resources/application.properties   # test-only H2 config
```

## How to Install

1. Install **JDK 17+**, **MySQL 8**, and **IntelliJ IDEA**.
2. Clone the repository and open it in IntelliJ IDEA as a Maven project.
3. You do **not** need to install Maven separately — this project includes the Maven
   Wrapper (`mvnw` / `mvnw.cmd`), which downloads the correct Maven version automatically.

## How to Create the MySQL Database

1. Start your local MySQL server.
2. Run the schema script:
   ```
   mysql -u root -p < database/robotics_club.sql
   ```
   Or open `database/robotics_club.sql` in MySQL Workbench and execute it.
3. This creates the `robotics_club` database, all 8 tables, foreign keys, the three
   roles (ADMIN, MENTOR, STUDENT), and a few sample members/equipment rows.

## How to Configure `application.properties`

Edit `src/main/resources/application.properties` and update the username/password
to match your local MySQL setup:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/robotics_club?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
```

**Never commit your real database password.** Keep local-only credentials out of Git
(see the Git Workflow section below).

## How to Run

From the project root:

```
./mvnw spring-boot:run        # Mac/Linux/Git Bash
mvnw.cmd spring-boot:run      # Windows CMD/PowerShell
```

Or run `RoboticsClubApplication.java` directly from IntelliJ IDEA.

The app starts on **http://localhost:8080**.

## Initial Login

A temporary in-memory account is configured in `SecurityConfig.java` so the group can
log in before Member 2 finishes database-based authentication:

- **Username:** `admin`
- **Password:** `admin123`

Member 2 will replace this with real database login backed by the `users` / `roles`
tables.

## Module Responsibilities

- **Member 1 (done in this initial setup):** Membership Management — full CRUD for
  club members (`Member`, `MemberRepository`, `MemberService`, `MemberController`,
  `templates/members/*`).
- **Members 2–7:** Own their respective modules as listed above. See
  [GROUP_HANDOVER.md](GROUP_HANDOVER.md) for exactly what each member needs to build.

## Git Workflow

- `main` — stable, shared branch.
- Each member works on their own branch (e.g. `member2-users`, `member3-equipment`, ...).
- Pull the latest `main` before starting work.
- Only modify files inside your own module (controller/service/repository/model/templates).
- Commit often with clear messages, push your branch, and open a Pull Request into `main`.
- Full branch naming convention and rules are in [GROUP_HANDOVER.md](GROUP_HANDOVER.md).
