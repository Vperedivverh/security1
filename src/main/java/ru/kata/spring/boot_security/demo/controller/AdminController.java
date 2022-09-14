package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

;

@Controller

public class AdminController {
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    public AdminController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", userService.getByUsername(userDetails.getUsername()));
        model.addAttribute("roles", userService.getByUsername(userDetails.getUsername()).getRoles());
        return "users";
    }

    @PostMapping("/admin")
    public String addNewUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String showEditPage(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @GetMapping("/admin/users/new")
    public String showAddNewUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "create";
    }

    @PutMapping("/admin/users/{id}")
    public String updateUserById(@PathVariable int id, @ModelAttribute("user") User user) {

        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/users/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
