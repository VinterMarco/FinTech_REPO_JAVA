package com.example.demo.common.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponseDTO {
    private String message;
    private String token;

    public MessageResponseDTO(String message) {
        this.message = message;
    }

    public MessageResponseDTO(String message, String token) {
        this.message = message;
        this.token = token;
    }
} 