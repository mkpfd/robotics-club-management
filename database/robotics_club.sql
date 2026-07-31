-- ============================================================
-- Robotics Club Management System - Database Schema
-- Run this whole file in MySQL (Workbench, CLI, etc.) to set up
-- the "robotics_club" database with all tables and sample data.
-- ============================================================

-- 1. Create and select the database
CREATE DATABASE IF NOT EXISTS robotics_club;
USE robotics_club;

-- Drop tables if they already exist (useful when re-running this script).
-- Order matters because of foreign keys: children before parents.
DROP TABLE IF EXISTS attendance;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS equipment_requests;
DROP TABLE IF EXISTS equipment;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS roles;

-- ============================================================
-- 2. roles table
-- ============================================================
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(20) NOT NULL UNIQUE
);

-- ============================================================
-- 3. members table (Member 1's module)
-- ============================================================
CREATE TABLE members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15),
    department VARCHAR(50),
    `year` INT,
    join_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- 4. users table (Member 2's module)
-- ============================================================
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
    member_id BIGINT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT fk_users_member FOREIGN KEY (member_id) REFERENCES members(id)
);

-- ============================================================
-- 5. equipment table (Member 3's module)
-- ============================================================
CREATE TABLE equipment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    quantity INT NOT NULL DEFAULT 0,
    available_quantity INT NOT NULL DEFAULT 0,
    description VARCHAR(255),
    location VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_equipment_quantity CHECK (quantity >= 0),
    CONSTRAINT chk_equipment_available CHECK (available_quantity >= 0)
);

-- ============================================================
-- 6. equipment_requests table (Member 4's module)
-- ============================================================
CREATE TABLE equipment_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    equipment_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    reason VARCHAR(255),
    request_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    approved_by BIGINT NULL,
    handover_date DATE NULL,
    return_date DATE NULL,
    CONSTRAINT fk_requests_member FOREIGN KEY (member_id) REFERENCES members(id),
    CONSTRAINT fk_requests_equipment FOREIGN KEY (equipment_id) REFERENCES equipment(id),
    CONSTRAINT fk_requests_approver FOREIGN KEY (approved_by) REFERENCES users(id)
);

-- ============================================================
-- 7. projects table (Member 5's module)
-- ============================================================
CREATE TABLE projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    start_date DATE,
    end_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'PLANNED',
    mentor_id BIGINT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_projects_mentor FOREIGN KEY (mentor_id) REFERENCES users(id)
);

-- ============================================================
-- 8. events table (Member 6's module)
-- ============================================================
CREATE TABLE events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    event_date DATE NOT NULL,
    event_time TIME,
    location VARCHAR(100),
    created_by BIGINT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_events_creator FOREIGN KEY (created_by) REFERENCES users(id)
);

-- ============================================================
-- 9. attendance table (Member 7's module)
-- ============================================================
CREATE TABLE attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    status VARCHAR(10) NOT NULL DEFAULT 'PRESENT',
    CONSTRAINT fk_attendance_event FOREIGN KEY (event_id) REFERENCES events(id),
    CONSTRAINT fk_attendance_member FOREIGN KEY (member_id) REFERENCES members(id)
);

-- ============================================================
-- Sample data
-- ============================================================

-- Roles
INSERT INTO roles (role_name) VALUES ('ADMIN'), ('MENTOR'), ('STUDENT');

-- Sample members
INSERT INTO members (student_id, name, email, phone, department, year, join_date, status) VALUES
('IT21001', 'Kavindu Perera', 'kavindu.perera@example.com', '0771234567', 'IT', 3, '2024-01-15', 'ACTIVE'),
('IT21002', 'Nimasha Silva', 'nimasha.silva@example.com', '0772345678', 'Computer Engineering', 2, '2024-02-10', 'ACTIVE'),
('IT21003', 'Sahan Fernando', 'sahan.fernando@example.com', '0773456789', 'IT', 4, '2023-09-01', 'INACTIVE');

-- Sample equipment
INSERT INTO equipment (name, category, quantity, available_quantity, description, location) VALUES
('Arduino Uno', 'Microcontroller', 20, 15, 'Arduino Uno R3 boards', 'Robotics Lab Shelf A'),
('Servo Motor SG90', 'Actuator', 30, 25, 'Small 9g servo motors', 'Robotics Lab Shelf B'),
('Soldering Iron', 'Tool', 10, 8, '60W soldering iron', 'Robotics Lab Tool Box');

-- Sample users
INSERT INTO users (id, username, password, role_id, member_id) VALUES
('admin', '$2a$10$Vl0mtMx7iVg.8r0BsqtA7ubmyO9gV9dTh1Qd0drhqrgXVF8mEUA/K','1', NULL);
