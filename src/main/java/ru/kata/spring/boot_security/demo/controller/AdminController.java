package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller

public class AdminController {
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    public AdminController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @PostMapping("/admin")
    public String saveUser(@ModelAttribute("user") User user) {
        User user1 = new User();
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setName(user.getName());
        user1.setSurname(user.getSurname());
        user1.setAddress(user.getAddress());
        user1.setRoles(user.getRoles());
        user1.setFn(user.getFn());
        user1.setId(user.getId());
        user1.setUsername(user.getUsername());
        userService.saveUser(user1);
        return "redirect:/admin";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @GetMapping("/admin/users/new")
    public String createUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "create";
    }

    @PostMapping("/admin/users/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("user") User user) {
        User user1 = new User();
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setName(user.getName());
        user1.setSurname(user.getSurname());
        user1.setAddress(user.getAddress());
        user1.setRoles(user.getRoles());
        user1.setFn(user.getFn());
        user1.setId(user.getId());
        user1.setUsername(user.getUsername());
        userService.updateUser(user1);
        return "redirect:/admin";
    }

    @GetMapping("/admin/users/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
