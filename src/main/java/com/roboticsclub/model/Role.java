package com.roboticsclub.model;

import jakarta.persistence.*;

/**
 * Represents a system role (ADMIN, MENTOR, STUDENT).
 * NOTE: This is a skeleton entity for the User & Role Management module.
 * Member 2 owns this module and may extend it as needed.
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true, length = 20)
    private String roleName;

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
