package com.example.demo.user.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.dto.CreateUserDTO;
import com.example.demo.user.dto.UserResponseDTO;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;  
import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.common.exception.DuplicateResourceException;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET ALL USERS
    public List<UserResponseDTO> getAllUsers() {
            List<UserEntity> userEntities = userRepository.findAll();
            List<UserResponseDTO> dtoList = new ArrayList<>();

            for (UserEntity user : userEntities) {
                UserResponseDTO dto = toResponse(user);
                dtoList.add(dto);
            }
            return dtoList;
    }

    // CREATE USER -- new
    public UserResponseDTO createUser(CreateUserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + dto.getEmail());
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateResourceException("Username already exists: " + dto.getUsername());
        }
        UserEntity saved = userRepository.save(toEntity(dto));
        return toResponse(saved);
    }
    // GET USER BY ID  -- new 
    public UserResponseDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.USER_NOT_FOUND,
                        "User",
                        id
                ));
    } 

    // DELETE USER -- new
    public UserResponseDTO  deleteUser(Long id) {
        return userRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                    ErrorCode.USER_NOT_FOUND,
                    "User",
                    id
                ));
    } 


    // ==================================================   
    //  DTO MAPPER
    public UserResponseDTO toResponse(UserEntity user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    public UserEntity toEntity(CreateUserDTO dto) {
        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }
    // ==================================================
}