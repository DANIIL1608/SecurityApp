package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidation;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;



@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserValidation validation;
    private final PasswordEncoder passwordEncoder;

    AdminController (UserService userService, RoleService roleService, UserValidation validation, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.validation = validation;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("allUsers", userService.getAll());
        return "pages/base";
    }
    @PostMapping
    public String addUser(@ModelAttribute("user") User user, @Valid BindingResult bindingResult) {
        validation.validate(user, bindingResult);
        if(bindingResult.hasErrors()) return "pages/create";
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "pages/create";
    }

    @GetMapping("/show")
    public String getUser(@RequestParam("id") int id, Model model) {
        model.addAttribute("singleUser", userService.findById(id));
        return "pages/update";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute User user, @RequestParam("id") int id, @Valid BindingResult bindingResult) {
        validation.validate(user, bindingResult);
        if (bindingResult.hasErrors()) return "pages/update";
        userService.update(id, user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        userService.delete(id);
        return "redirect:/admin";
    }
}
