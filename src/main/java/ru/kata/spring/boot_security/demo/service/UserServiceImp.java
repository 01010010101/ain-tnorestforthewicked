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
import java.util.Optional;

@Service
public class UserServiceImp implements UserDetailsService, UserService {

    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepositories userRepositories, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepositories = userRepositories;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void updateUser(int id, User user) {
        user.setId(id);
        userRepositories.save(user);
    }


    public User getUserAtId(Integer id) {
        Optional<User> findUser = userRepositories.findById(id);
        return findUser.orElse(null);
    }


    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepositories.save(user);
    }

    @Transactional
    public void removeUserById(Integer id) {
        userRepositories.delete(getUserAtId(id));
    }


    public List<User> getAllUsers() {
        return userRepositories.findAll();
    }


    public User findByName(String name) {
        return userRepositories.findByName(name);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByName(username);
        if(user==null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());

    }
}
