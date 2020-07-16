package com.order.repository;

import com.order.domain.User;
import org.jooq.DSLContext;

import java.util.Optional;

import static com.order.database.jooq.tables.OrderUser.ORDER_USER;


public class SqlUserRepository implements UserRepository {

    private final DSLContext dsl;

    public SqlUserRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return dsl.selectFrom(ORDER_USER)
                .where(ORDER_USER.EMAIL.eq(email))
                .fetchOptional()
                .map(record -> new User(record.getId(),record.getUsername(), record.getEmail(), record.getPassword(), record.getIsActive()));
    }

    @Override
    public Optional<User> getById(long id) {
        return dsl.selectFrom(ORDER_USER)
                .where(ORDER_USER.ID.eq(id))
                .fetchOptional()
                .map(record -> new User(record.getUsername(), record.getEmail(), record.getPassword()));
    }

    @Override
    public User createUser(User user) {
        return dsl.insertInto(ORDER_USER, ORDER_USER.USERNAME, ORDER_USER.EMAIL, ORDER_USER.PASSWORD)
                .values(user.username, user.email, user.password)
                .returning(ORDER_USER.USERNAME, ORDER_USER.EMAIL, ORDER_USER.PASSWORD, ORDER_USER.IS_ACTIVE)
                .fetchOne()
                .map(us -> new User(
                        us.getValue(ORDER_USER.USERNAME),
                        us.getValue(ORDER_USER.EMAIL),
                        us.getValue(ORDER_USER.PASSWORD)));
    }
}
