# Robotics Club Management System

A simple web application for managing a university robotics club. members, users/roles,equipment, equipment requests, projects, events and attendance. Built as a IT - 3003 3rd year university group mini project.

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
| Kaveesh | Membership Management |
| sakindu | User & Role Management |
| sandaka | Inventory Management (Equipment) |
| dulan | Equipment Request Management |
| athukorala | Project Management |
| pasindu | Event Management |
| athintha | Attendance Management |


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

Edit `src/main/resources/application.properties` and update the username/password to match your local MySQL setup:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/robotics_club?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
```

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

A temporary in memory account is configured in `SecurityConfig.java` so the group can log in using:

- **Username:** `admin`
- **Password:** `admin123`

sakindu will replace this with real database login backed by the `users` / `roles` tables.
