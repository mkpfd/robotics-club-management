package com.roboticsclub.service;

import com.roboticsclub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * NOTE: Skeleton service for the User & Role Management module.
 * Member 2 owns this module and should add the CRUD methods here
 * (getAllUsers, getUserById, saveUser, deleteUser, etc.), following
 * the same pattern used in MemberService.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TODO (Member 2): implement user management methods here.
}
