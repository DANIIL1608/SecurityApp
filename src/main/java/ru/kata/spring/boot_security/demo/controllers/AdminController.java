package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidation;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserValidation validation;

    AdminController (UserService userService, UserValidation validation) {
        this.userService = userService;
        this.validation = validation;
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
