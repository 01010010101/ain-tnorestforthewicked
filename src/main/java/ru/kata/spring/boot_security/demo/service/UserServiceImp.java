package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepositories;


import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImp implements UserDetailsService, UserService {

    private final UserRepositories userRepositories;
    private final PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserServiceImp(UserRepositories userRepositories, @Lazy PasswordEncoder bCryptPasswordEncoder) {
        this.userRepositories = userRepositories;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (!user.getPassword().equals(Objects.requireNonNull(userRepositories.findById(user.getId()).orElse(null)).getPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }else {
            user.setPassword(Objects.requireNonNull(userRepositories.findById(user.getId()).orElse(null)).getPassword());
        }

        userRepositories.save(user);
    }


    @Override
    public User getUserAtId(Integer id) {
        return userRepositories.findById(id).orElse(null);
    }

    @Override
    public void updateUser(int id, User user) {

    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepositories.save(user);
    }

    @Override
    @Transactional
    public void removeUserById(Integer id) {
        userRepositories.delete(getUserAtId(id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositories.findAll();
    }

    @Override
    public User findByName(String name) {
        return userRepositories.findByName(name);
    }

    @Override
    public User findByEmail(String username) {
        return userRepositories.findByEmail(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositories.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getRoles());
    }
}
