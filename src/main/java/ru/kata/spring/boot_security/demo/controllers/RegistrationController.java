package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidation;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final UserValidation userValidation;

    public RegistrationController( UserService userService,  UserValidation userValidation) {
        this.userService = userService;
        this.userValidation = userValidation;
    }
    @GetMapping("/index")
    public String startPage() {
        return "index";
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
        userService.save(user);
        return "redirect:/login";
    }
}