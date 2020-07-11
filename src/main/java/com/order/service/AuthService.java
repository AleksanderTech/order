package com.order.service;

import com.order.error.Errors;
import com.order.error.HttpStatus;
import com.order.error.OrderException;
import com.order.model.User;
import com.order.repository.SqlUserRepository;
import com.sun.net.httpserver.HttpsServer;

public class AuthService extends Service {

    private final SqlUserRepository userRepository;
    private final Hasher hasher;

    public AuthService(SqlUserRepository userRepository, Hasher hasher) {
        this.userRepository = userRepository;
        this.hasher = hasher;
    }

    public Response<User> signIn(User user) {
        return response(() -> signInAction(user));
    }

    public User signInAction(User user) {
        User originalUser = userRepository.getByUsername(user.username).orElseThrow(() -> new OrderException(HttpStatus.NOT_FOUND, Errors.USER_NOT_FOUND));
        if (hasher.validatePassword(user.password, originalUser.password)) {
            return originalUser;
        } else {
            throw new OrderException(HttpStatus.UNAUTHORIZED, Errors.INCORRECT_PASSWORD);
        }
    }

    public void signUp(User user) {
        userRepository.getByUsername(user.username)
                .ifPresentOrElse(
                        us -> {
                            throw new OrderException(HttpStatus.CONFLICT, Errors.USERNAME_ALREADY_EXISTS);
                        },
                        () -> {
                            user.password = hasher.hash(user.password);
                            userRepository.createUser(user);
                        });
    }
}
