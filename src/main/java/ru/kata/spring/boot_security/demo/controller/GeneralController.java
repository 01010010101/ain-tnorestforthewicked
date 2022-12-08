package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@org.springframework.stereotype.Controller
@RequestMapping()
public class GeneralController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public GeneralController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping("/admin/new")
    public String createUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "admin/new";
    }

    @PostMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/";
    }


    @RequestMapping("/user")
    public String printUser(ModelMap model, Principal principal) {
        model.addAttribute("user", userService.findByName(principal.getName()));

        return "user";
    }

    @RequestMapping("/")
    public String printUsers() {

        return "redirect:/user";
    }

    @RequestMapping("/admin")
    public String printUsersForAdmin(ModelMap model, Principal principal) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("user", userService.findByName(principal.getName()));

        model.addAttribute("newUser", new User());
        model.addAttribute("userRoles", roleService.findAll());

        return "/admin/users";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUserById(id);

        return "redirect:/admin/";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateUser(id, user);

        return "redirect:/admin/";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserAtId(id));

        return "/admin/edit";
    }

}
