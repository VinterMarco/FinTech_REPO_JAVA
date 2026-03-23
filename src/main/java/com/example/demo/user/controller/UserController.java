package com.example.demo.user.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.service.UserService;
import org.springframework.http.ResponseEntity;        // ← ASTA LIPSEȘTE


import java.util.List;
import java.util.Optional;



// Day 4 – CRUD Operations; 
//         Create POST /users; 
//         Create GET /users; 
//         Create GET /users/{id}; 
//         Create DELETE /users; 
//         Test all endpoints with Postman


// Day 5 – DTOs + Validation; 
//         Create DTO classes; 
//         Map entity ↔ DTO; 
//         Add validation annotations (@Email, @NotNull); 
//         Test invalid input

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    // @GetMapping("/{id}")
    // public Optional<UserEntity> getUserById(@PathVariable Long id) {
    //     return userService.getUserById(id);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}