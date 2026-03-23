package com.example.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.user.entity.UserEntity;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // Optional: find by username
    Optional<UserEntity> findByUsername(String username);
}