package com.example.demo.user.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.service.UserService;
import org.springframework.http.ResponseEntity;       
import com.example.demo.user.dto.CreateUserDTO;
import com.example.demo.user.dto.UserResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;



//     Day 6 Exception Handling; 
//         - Create global exception handler; 
//         - Add custom exceptions (UserNotFound); 
//         - Return proper HTTP codes; 
//         - Test error responses



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
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE USER BY ID 
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDTO>  deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
}