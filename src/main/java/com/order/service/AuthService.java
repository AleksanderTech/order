package com.order.service;

import com.order.error.Errors;
import com.order.error.HttpStatus;
import com.order.error.OrderException;
import com.order.domain.User;
import com.order.repository.SqlUserRepository;

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

    public Response<Void> signUp(User user) {
        return response(() -> signUpAction(user));
    }

    public User signInAction(User user) {
        User originalUser = userRepository.getByEmail(user.email).orElseThrow(() -> new OrderException(HttpStatus.NOT_FOUND, Errors.USER_NOT_FOUND));
        if (hasher.validatePassword(user.password, originalUser.password)) {
            return originalUser;
        } else {
            throw new OrderException(HttpStatus.UNAUTHORIZED, Errors.INCORRECT_PASSWORD);
        }
    }

    public void signUpAction(User user) {
        userRepository.getByEmail(user.email)
                .ifPresentOrElse(
                        us -> {
                            throw new OrderException(HttpStatus.CONFLICT, Errors.EMAIL_EXISTS);
                        },
                        () -> {
                            user.password = hasher.hash(user.password);
                            userRepository.createUser(user);
                        });
    }
}
