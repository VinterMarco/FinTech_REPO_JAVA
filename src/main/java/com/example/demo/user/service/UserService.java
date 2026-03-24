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

    // GET USER BY ID
    public Optional<UserResponseDTO> getUserById(Long id) {
        Optional<UserEntity> userEntityOpt = userRepository.findById(id);

        if (userEntityOpt.isPresent()) {
            UserResponseDTO dto = toResponse(userEntityOpt.get());
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }


    // CREATE USER 
    public UserResponseDTO createUser(CreateUserDTO dto) {
        UserEntity user = toEntity(dto);
        UserEntity saved = userRepository.save(user);
        return toResponse(saved);
    }


    // DELETE USER
    public Optional<UserResponseDTO>  deleteUser(Long id) {
        Optional<UserEntity> userEntityOpt = userRepository.findById(id);

        if (userEntityOpt.isPresent()) {
            UserResponseDTO dto = toResponse(userEntityOpt.get());
            userRepository.deleteById(id);
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
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