package com.BackendProject.Shopeeee.repository;

import com.BackendProject.Shopeeee.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    User findByEmail(String email);

   User getUserByUsername(String username);
}
