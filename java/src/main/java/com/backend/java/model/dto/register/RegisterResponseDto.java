package com.backend.java.model.dto.register;
public class RegisterResponseDto {

    public RegisterResponseDto() {
        this.message = "User created successfully.";
    }

    private String message;

    String getMessage() {
        return message;
    }
}
