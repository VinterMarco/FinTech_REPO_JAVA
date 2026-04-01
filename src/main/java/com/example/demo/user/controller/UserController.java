package com.example.demo.user.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.user.service.UserService;
import com.example.demo.user.dto.CreateUserDTO;
import com.example.demo.user.dto.UserResponseDTO;
import com.example.demo.user.dto.DeleteUserDTO;
import com.example.demo.user.dto.UpdateUserDTO;
import jakarta.validation.Valid;
import java.util.List;

// Day 7 – Refactor + Review;  ()
// Test all endpoints; 
// Document code

// Day 8 – Spring Security Setup; 
// Add Spring Security dependency; 
// Configure security; 
// Disable default login page; 
// Test protected endpoints

// TODAY BEFORE TRIP AFTER GYM


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // CREATE USER 
    @PostMapping
    public UserResponseDTO createUser(@RequestBody @Valid CreateUserDTO dto) {
        return userService.createUser(dto);
    }
    

    // GET ALL USERS
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }
  

    // GET USER BY ID   
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // DELETE USER BY ID 
    @DeleteMapping("/{id}")
    public DeleteUserDTO deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    // UPFATE BY ID 
    @PatchMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDTO dto) {
        return userService.updateUser(id, dto);
    }
    
}