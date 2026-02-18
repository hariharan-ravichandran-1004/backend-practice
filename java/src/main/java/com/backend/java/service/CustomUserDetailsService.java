package com.backend.java.service;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.backend.java.model.entity.UserEntity;
import com.backend.java.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   @Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword()) // MUST be hashed password
            .authorities("USER")
            .build();
}

}
