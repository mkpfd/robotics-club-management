package com.roboticsclub.controller;

import com.roboticsclub.model.User;
import com.roboticsclub.repository.RoleRepository;
import com.roboticsclub.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository) {

        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    // Display all users
    @GetMapping
    public String listUsers(Model model) {

        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    // Show add user form
    @GetMapping("/new")
    public String newUserForm(Model model) {

        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "users/form";
    }

    // Save user
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {

        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "User saved successfully.");
        return "redirect:/users";
    }

    // Edit user
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {

        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "users/form";
    }

    // Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully.");

        return "redirect:/users";
    }
}