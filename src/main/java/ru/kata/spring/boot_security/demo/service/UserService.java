package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void updateUser(User user);

    void updateUser(int id, User user);

    void saveUser(User user);

    void removeUserById(Integer id);

    List<User> getAllUsers();

    User getUserAtId(Integer id);

    User findByName(String name);

    User findByEmail(String username);
}
