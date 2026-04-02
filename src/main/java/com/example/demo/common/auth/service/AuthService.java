package com.example.demo.common.auth.service;
import org.springframework.stereotype.Service;

import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.common.auth.dto.RegisterRequestDTO;
import com.example.demo.common.auth.dto.LoginRequestDTO;
import com.example.demo.common.auth.dto.MessageResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.common.exception.DuplicateResourceException;
import com.example.demo.common.exception.InvalidCredentialsException;
import com.example.demo.common.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    

    // CREATE USER 
    public MessageResponseDTO register(RegisterRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Account already exists with this email", ErrorCode.DUPLICATE_RESOURCE);
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateResourceException("Account already exists with this username", ErrorCode.DUPLICATE_RESOURCE);
        }

        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());  
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);

        return new MessageResponseDTO("User registered successfully");

    }


    // LOGIN USER 
    public MessageResponseDTO login(LoginRequestDTO dto) {

        UserEntity  user = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(() ->  new InvalidCredentialsException(
                "Username or Password are incorect", 
                ErrorCode.INVALID_CREDENTIALS));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException(
                "Username or Password are incorect", 
                ErrorCode.INVALID_CREDENTIALS);
        }    

        return new MessageResponseDTO("Login successful", jwtService.generateToken(user.getEmail()));
    }


}
