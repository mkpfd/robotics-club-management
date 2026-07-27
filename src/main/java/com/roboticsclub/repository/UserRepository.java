package com.roboticsclub.repository;

import com.roboticsclub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * NOTE: Skeleton repository for the User & Role Management module (Member 2).
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
