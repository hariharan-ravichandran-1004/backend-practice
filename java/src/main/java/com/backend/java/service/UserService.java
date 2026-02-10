package com.backend.java.service;

public interface UserService {
    void register(String username, String password);
    void login(String email,String password);
}
