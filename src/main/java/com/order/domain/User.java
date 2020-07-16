package com.order.domain;

public class User {

    public long id;
    public String username;
    public String email;
    public String password;
    public boolean isActive = true;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(long id, String username, String email, String password, boolean isActive) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
