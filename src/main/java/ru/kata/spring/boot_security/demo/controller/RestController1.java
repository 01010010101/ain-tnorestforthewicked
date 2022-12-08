package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@RestController()
public class RestController1 {

    private final UserService userService;

    private final RoleService roleService;

    public RestController1(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> getAll = userService.getAllUsers();
        return getAll != null && !getAll.isEmpty()
                ? new ResponseEntity<>(getAll, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) {
        User user = userService.getUserAtId(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        userService.saveUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        userService.removeUserById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/viewUser")
    public ResponseEntity<User> showUser(Authentication auth) {
        return new ResponseEntity<>(userService.findByName(auth.getName()), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    ResponseEntity<Role> getRoleById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(roleService.findById(id), HttpStatus.OK);
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.updateUser(user.getId(), user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
