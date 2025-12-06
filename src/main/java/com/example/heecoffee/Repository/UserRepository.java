package com.example.heecoffee.Repository;

import com.example.heecoffee.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    String email(String email);
}
