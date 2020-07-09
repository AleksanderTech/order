package com.order.service;

import com.order.error.Errors;
import com.order.error.UsernameAlreadyExists;
import com.order.model.User;
import com.order.repository.SqlUserRepository;

public class AuthService extends Service{

    private final SqlUserRepository userRepository;
    private final Hasher hasher;

    public AuthService(SqlUserRepository userRepository, Hasher hasher) {
        this.userRepository = userRepository;
        this.hasher = hasher;
    }

    //    public User signIn(User user) {
//        User originalUser = userRepository.getByUsername(user.username).orElseThrow(RuntimeException::new);
//        if (hasher.validatePassword(user.password, originalUser.password)) {
//            return originalUser;
//        } else {
//            throw new RuntimeException();
//        }
//    }

    public Response<User> signIn(User user) {
        return response(()->signInAction(user));
    }

    public User signInAction(User user){
        if(1==1){
            throw new RuntimeException();
        }

        User originalUser = userRepository.getByUsername(user.username).orElseThrow(RuntimeException::new);
        if (hasher.validatePassword(user.password, originalUser.password)) {
            return originalUser;
        } else {
            throw new RuntimeException();
        }
    }

    public void signUp(User user) {
        userRepository.getByUsername(user.username)
                .ifPresentOrElse(
                        us -> {
                            throw new UsernameAlreadyExists(Errors.USERNAME_ALREADY_EXISTS);
                        },
                        () -> {
                            user.password = hasher.hash(user.password);
                            userRepository.createUser(user);
                        });
    }
}
