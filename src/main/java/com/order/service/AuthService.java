package com.order.service;

import com.order.model.User;
import com.order.repository.SqlUserRepository;

public class AuthService {

    private final SqlUserRepository userRepository;

    public AuthService(SqlUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(User user) {
        userRepository.getByUsername(user.username)
                .ifPresentOrElse(
                        us -> {
                            throw new RuntimeException("User with provided username already exists");
                        },
                        () -> {
                            userRepository.createUser(user);
                        });
    }
}
