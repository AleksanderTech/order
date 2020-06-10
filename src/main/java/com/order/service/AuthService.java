package com.order.service;

import com.order.error.Message;
import com.order.error.UsernameAlreadyExists;
import com.order.model.User;
import com.order.repository.SqlUserRepository;

public class AuthService {

    private final SqlUserRepository userRepository;
    private final Hasher hasher;

    public AuthService(SqlUserRepository userRepository, Hasher hasher) {
        this.userRepository = userRepository;
        this.hasher = hasher;
    }

    public void signIn(User user){
        userRepository.getByUsername(user.username)
                .ifPresentOrElse(
                        us -> {
                            if(hasher.validatePassword(user.password,us.password)){
                                System.out.println("User sign in");
                            }else{
                                throw new RuntimeException();
                            }
                        },
                        () -> {
                            throw new RuntimeException();
                        }
                );
    }

    public void signUp(User user) {
        userRepository.getByUsername(user.username)
                .ifPresentOrElse(
                        us -> {
                            throw new UsernameAlreadyExists(Message.USER_USERNAME_ALREADY_EXISTS);
                        },
                        () -> {
                            user.password = hasher.hash(user.password);
                            userRepository.createUser(user);
                        });
    }
}
