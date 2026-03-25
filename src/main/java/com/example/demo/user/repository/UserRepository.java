package com.example.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.user.entity.UserEntity;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}