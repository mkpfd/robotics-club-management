package com.roboticsclub.controller;

import com.roboticsclub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * NOTE: Skeleton controller for the User & Role Management module.
 * Member 2 owns this module and should add routes here (GET /users,
 * GET /users/new, POST /users/save, GET /users/edit/{id}, GET /users/delete/{id}),
 * following the same pattern used in MemberController.
 */
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // TODO (Member 2): implement user management routes here.
}
