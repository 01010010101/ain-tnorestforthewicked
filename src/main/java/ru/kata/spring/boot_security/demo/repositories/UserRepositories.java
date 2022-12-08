package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;

@Repository
public interface UserRepositories extends JpaRepository<User, Integer> {
    User findByName(String name);
    @Query("select p from User p join fetch p.roles where p.email = :email")
    User findByEmail(String email);

    Optional<User> findById(Integer id);

}
