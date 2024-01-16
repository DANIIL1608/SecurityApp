package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidation;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
public class RegistrationController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserValidation userValidation;

    private final RoleService roleService;

    public RegistrationController(PasswordEncoder passwordEncoder, UserService userService, RoleService roleService, UserValidation userValidation, RoleService roleService1) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userValidation = userValidation;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "security/login";
    }
    @GetMapping("/reg")
    public String registration(@ModelAttribute("user") User user) {
        return "security/registration";
    }

    @PostMapping("/reg")
    public String processRegistration(@ModelAttribute("user") User user, @Valid BindingResult bindingResult) {
        userValidation.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "security/registration";
        Set<Role> roles = new HashSet<>();
        if(user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
            roles.add(roleService.findById(2));
        } else {
            roles.add(roleService.findById(1));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/login";
    }
}