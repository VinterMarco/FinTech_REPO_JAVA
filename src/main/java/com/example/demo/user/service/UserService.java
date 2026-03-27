package com.example.demo.user.service;

import org.springframework.stereotype.Service;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.dto.CreateUserDTO;
import com.example.demo.user.dto.UpdateUserDTO;
import com.example.demo.user.dto.UserResponseDTO;
import com.example.demo.user.dto.DeleteUserDTO;
import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.common.exception.DuplicateResourceException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for user management operations.
 * Handles business logic for creating, retrieving, updating, and deleting users.
 * Validation and duplicate checks are performed before any persistence operation.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return list of all users as UserResponseDTO
     */
    public List<UserResponseDTO> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserResponseDTO> dtoList = new ArrayList<>();
        for (UserEntity user : userEntities) {
            dtoList.add(toResponse(user));
        }
        return dtoList;
    }

    /**
     * Creates a new user after validating that email and username are unique.
     *
     * @param dto the user creation payload
     * @return the created user as UserResponseDTO
     * @throws DuplicateResourceException if email or username already exists
     */
    public UserResponseDTO createUser(CreateUserDTO dto) {
        validateNewEmail(dto.getEmail());
        validateNewUsername(dto.getUsername());
        UserEntity saved = userRepository.save(toEntity(dto));
        return toResponse(saved);
    }

    /**
     * Retrieves a single user by their ID.
     *
     * @param id the user's ID
     * @return the user as UserResponseDTO
     * @throws ResourceNotFoundException if no user exists with the given ID
     */
    public UserResponseDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.USER_NOT_FOUND, "User", id
                ));
    }

    /**
     * Deletes a user by their ID and returns the deleted user's data.
     *
     * @param id the user's ID
     * @return the deleted user as DeleteUserDTO with deletedAt timestamp
     * @throws ResourceNotFoundException if no user exists with the given ID
     */
    public DeleteUserDTO deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.USER_NOT_FOUND, "User", id
                ));
        DeleteUserDTO dto = toDeleteResponse(user);
        userRepository.delete(user);
        return dto;
    }

    /**
     * Updates the username and email of an existing user.
     * Only validates uniqueness if the value actually changed.
     *
     * @param id  the user's ID
     * @param dto the update payload containing new username and email
     * @return the updated user as UserResponseDTO
     * @throws ResourceNotFoundException  if no user exists with the given ID
     * @throws DuplicateResourceException if the new email or username is already taken
     */
    public UserResponseDTO updateUser(Long id, UpdateUserDTO dto) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.USER_NOT_FOUND, "User", id
                ));
        validateUniqueEmail(user, dto.getEmail());
        validateUniqueUsername(user, dto.getUsername());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return toResponse(userRepository.save(user));
    }

    // ========================= HELPER FUNCTIONS ==========================

    /**
     * Validates that the email is not already taken — used during user creation.
     *
     * @throws DuplicateResourceException if email already exists
     */
    private void validateNewEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Email already exists: " + email);
        }
    }

    /**
     * Validates that the username is not already taken — used during user creation.
     *
     * @throws DuplicateResourceException if username already exists
     */
    private void validateNewUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateResourceException("Username already exists: " + username);
        }
    }

    /**
     * Validates email uniqueness during update.
     * Skips validation if the email hasn't changed.
     *
     * @throws DuplicateResourceException if the new email is already taken by another user
     */
    private void validateUniqueEmail(UserEntity user, String newEmail) {
        if (!user.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)) {
            throw new DuplicateResourceException("Email already exists: " + newEmail);
        }
    }

    /**
     * Validates username uniqueness during update.
     * Skips validation if the username hasn't changed.
     *
     * @throws DuplicateResourceException if the new username is already taken by another user
     */
    private void validateUniqueUsername(UserEntity user, String newUsername) {
        if (!user.getUsername().equals(newUsername) && userRepository.existsByUsername(newUsername)) {
            throw new DuplicateResourceException("Username already exists: " + newUsername);
        }
    }

    // ===================== DTO MAPPERS =============================

    /**
     * Maps a UserEntity to a UserResponseDTO.
     */
    public UserResponseDTO toResponse(UserEntity user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    /**
     * Maps a CreateUserDTO to a UserEntity.
     * createdAt is not set here — handled automatically by @PrePersist.
     */
    public UserEntity toEntity(CreateUserDTO dto) {
        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    /**
     * Maps a UserEntity to a DeleteUserDTO.
     * Sets deletedAt to the current timestamp at the moment of deletion.
     */
    public DeleteUserDTO toDeleteResponse(UserEntity user) {
        DeleteUserDTO dto = new DeleteUserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setDeletedAt(LocalDateTime.now());
        return dto;
    }

    // ====================END OF DTO MAPPERS===========================
}