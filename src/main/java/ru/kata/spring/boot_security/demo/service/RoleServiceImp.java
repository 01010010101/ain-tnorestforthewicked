package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepositories;

import java.util.List;
@Service
@Transactional
public class RoleServiceImp implements RoleService{
    RoleRepositories roleRepositories;

    @Autowired
    public RoleServiceImp(RoleRepositories roleRepositories) {
        this.roleRepositories = roleRepositories;
    }

    public List<Role> findAll() {
        return roleRepositories.findAll();
    }

    @Transactional
    public void addRole(Role role) {
       List<Role> roles = findAll();
       if (! (roles.contains(role))) {
           roleRepositories.save(role);
       }
    }

}
