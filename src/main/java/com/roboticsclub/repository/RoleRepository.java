package com.roboticsclub.repository;

import com.roboticsclub.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * NOTE: Skeleton repository for the User & Role Management module (Member 2).
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
