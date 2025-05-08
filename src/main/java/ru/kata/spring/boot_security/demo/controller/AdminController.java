package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String admin(Model model, Principal principal) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());

        if (principal != null) {
            User loggedUser = userService.getUserByUsername(principal.getName());
            model.addAttribute("user", loggedUser);
        }
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/update-user/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute User updatedUser,
                             @RequestParam(value = "roles", required = false) List<Long> roleIds,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin?error=validation";
        }

        userService.updateUser(id, updatedUser, roleIds);
        return "redirect:/admin";
    }


}
