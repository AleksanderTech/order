package com.order.repository;

import com.order.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> getByEmail(String username);

    Optional<User> getById(long id);

    User createUser(User user);
}
