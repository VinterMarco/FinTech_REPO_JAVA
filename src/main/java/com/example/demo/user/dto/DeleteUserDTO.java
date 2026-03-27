package com.example.demo.user.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeleteUserDTO {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime deletedAt;
}