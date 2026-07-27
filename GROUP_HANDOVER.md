# Group Handover — Robotics Club Management System

This document explains what's already done, how to run the project, and exactly what
each of the 7 group members should build next.

## 1. What Has Already Been Completed

- Full Spring Boot + Maven + Thymeleaf + MySQL project skeleton.
- Complete database schema for **all 8 tables** (`database/robotics_club.sql`), including
  foreign keys and sample data — so everyone's module has a database to build against
  from day one.
- Basic Spring Security setup with a temporary in-memory login (`admin` / `admin123`).
- Shared dashboard page with navigation links to every module.
- **Member 1's Membership Management module — fully implemented and tested**: entity,
  repository, service, controller, list/form views, validation.
- Skeleton `User`, `Role` entities and `UserRepository`, `RoleRepository`,
  `UserService`, `UserController` classes for Member 2 to build on.
- A Maven Wrapper (`mvnw`) so nobody needs to install Maven manually.

## 2. Database Structure

Database name: **`robotics_club`** (everyone must use this exact name).

| Table | Purpose | Key relationships |
|---|---|---|
| `roles` | ADMIN / MENTOR / STUDENT | referenced by `users.role_id` |
| `members` | Club members (Member 1) | referenced by `users.member_id`, `equipment_requests.member_id`, `attendance.member_id` |
| `users` | Login accounts (Member 2) | `role_id → roles.id`, `member_id → members.id` (nullable) |
| `equipment` | Inventory (Member 3) | referenced by `equipment_requests.equipment_id` |
| `equipment_requests` | Borrow requests (Member 4) | `member_id`, `equipment_id`, `approved_by → users.id` |
| `projects` | Club projects (Member 5) | `mentor_id → users.id` |
| `events` | Club events (Member 6) | `created_by → users.id` |
| `attendance` | Event attendance (Member 7) | `event_id → events.id`, `member_id → members.id` |

Full column definitions are in `database/robotics_club.sql` — read that file, it's
short and commented.

`spring.jpa.hibernate.ddl-auto=update` is set in `application.properties`, so Hibernate
will also auto-create/update tables to match your `@Entity` classes. The SQL file is
still the source of truth for the intended schema — if your entity doesn't match it,
fix the entity, not the other way around.

## 3. How to Run the Application

```
./mvnw spring-boot:run
```

(or run `RoboticsClubApplication.java` in IntelliJ). App runs at `http://localhost:8080`.

## 4. How to Connect MySQL

1. Make sure MySQL is running locally.
2. Run `database/robotics_club.sql` once to create the database and tables.
3. Set your own username/password in `src/main/resources/application.properties`
   (do not commit real passwords).

## 5. What Member 1 Completed

Membership Management — full CRUD:

- `model/Member.java`, `repository/MemberRepository.java`, `service/MemberService.java`,
  `controller/MemberController.java`
- `templates/members/list.html`, `templates/members/form.html`
- Validation on studentId/name/email/joinDate/status, unique studentId/email checks
- Automated test suite: `src/test/java/com/roboticsclub/MemberCrudTests.java`
  (runs against an in-memory H2 database so it works without MySQL installed; still
  test your own module against real MySQL before you rely on it)

## 6. What Member 2 Should Do — User & Role Management

Skeleton already exists: `model/User.java`, `model/Role.java`,
`repository/UserRepository.java`, `repository/RoleRepository.java`,
`service/UserService.java`, `controller/UserController.java`.

Build:
- Full CRUD for users (list/create/edit/delete), following the same pattern as
  `MemberController`/`MemberService`.
- `templates/users/list.html`, `templates/users/form.html`.
- Replace the temporary in-memory admin account in `config/SecurityConfig.java` with a
  real `UserDetailsService` backed by the `users`/`roles` tables (password hashing with
  `BCryptPasswordEncoder` is already configured — reuse the existing bean).
- Role-based access rules (e.g. only ADMIN can manage users).

## 7. What Member 3 Should Do — Inventory Management (Equipment)

- Create `model/Equipment.java`, `repository/EquipmentRepository.java`,
  `service/EquipmentService.java`, `controller/EquipmentController.java`.
- `templates/equipment/list.html`, `templates/equipment/form.html`.
- Table already exists: `equipment` (see schema). Keep `quantity` and
  `available_quantity` consistent (`available_quantity <= quantity`).

## 8. What Member 4 Should Do — Equipment Request Management

- Create `model/EquipmentRequest.java`, `repository/EquipmentRequestRepository.java`,
  `service/RequestService.java`, `controller/RequestController.java`.
- `templates/requests/list.html`, `templates/requests/form.html`.
- Table already exists: `equipment_requests`, with status values `PENDING`, `APPROVED`,
  `REJECTED`, `HANDED_OVER`, `RETURNED`.
- Depends on `members` (Member 1), `equipment` (Member 3) and `users` (Member 2, for
  `approved_by`) already existing — coordinate with them if you need those entities.

## 9. What Member 5 Should Do — Project Management

- Create `model/Project.java`, `repository/ProjectRepository.java`,
  `service/ProjectService.java`, `controller/ProjectController.java`.
- `templates/projects/list.html`, `templates/projects/form.html`.
- Table already exists: `projects`, with status values `PLANNED`, `ONGOING`,
  `COMPLETED`, `CANCELLED`, and `mentor_id → users.id`.

## 10. What Member 6 Should Do — Event Management

- Create `model/Event.java`, `repository/EventRepository.java`,
  `service/EventService.java`, `controller/EventController.java`.
- `templates/events/list.html`, `templates/events/form.html`.
- Table already exists: `events`, with `created_by → users.id`.

## 11. What Member 7 Should Do — Attendance Management

- Create `model/Attendance.java`, `repository/AttendanceRepository.java`,
  `service/AttendanceService.java`, `controller/AttendanceController.java`.
- `templates/attendance/list.html`, `templates/attendance/form.html`.
- Table already exists: `attendance`, with `event_id → events.id`,
  `member_id → members.id`, status `PRESENT`/`ABSENT`.
- Depends on `events` (Member 6) and `members` (Member 1) existing.

## 12. Git Branch Naming Convention

```
main                  # shared, stable branch
member1-members       # Membership Management (done)
member2-users         # User & Role Management
member3-equipment      # Inventory Management
member4-requests       # Equipment Request Management
member5-projects       # Project Management
member6-events         # Event Management
member7-attendance     # Attendance Management
```

## 13. Important Rules for Modifying Shared Files

- **Database (`database/robotics_club.sql`)**: shared. Everyone uses the same
  `robotics_club` database and the same table structure. If you need a schema change:
  1. Tell the group first.
  2. Update `database/robotics_club.sql`.
  3. Explain why in your PR description.
  4. Commit the schema change separately from your feature code.
- **`SecurityConfig.java`**: owned by Member 2. Others should not edit it — ask Member 2
  if your module needs a new access rule.
- **`dashboard.html` / `fragments/navbar.html`**: shared. If you need to change the
  nav bar or dashboard cards, coordinate with the group instead of editing silently.
- **Do not** rename or remove another member's files, columns, or routes without asking.
- Never commit real database passwords or `.idea/`/`target/` — they're already excluded
  via `.gitignore`.

### Step-by-step for every member

1. Clone the repository.
2. Open it in IntelliJ IDEA.
3. Create your branch from `main` (e.g. `git checkout -b member3-equipment`).
4. Set your local MySQL password in `application.properties` (don't commit it).
5. Pull the latest `main` before you start coding each session.
6. Only work inside your own module's files.
7. Test your CRUD locally (build, run, click through create/edit/delete) before pushing.
8. Commit multiple times with clear messages as you go.
9. Push your branch: `git push -u origin member3-equipment`.
10. Open a Pull Request into `main` on GitHub.
11. Be ready to explain your own module's code during the viva.
