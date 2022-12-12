package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    public void updateUser(int id, User user);

    public void saveUser(User user);

    public void removeUserById(Integer id);

    public List<User> getAllUsers();

    public User getUserAtId(Integer id);

    public User findByName(String name);
}
