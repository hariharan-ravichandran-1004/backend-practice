package com.backend.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.java.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
        boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
