package com.backend.java.model.dto.user;

public class UserResponseDto {

    private Long id;
    private String email;

    public UserResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
