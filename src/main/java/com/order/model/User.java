package com.order.model;

public class User {

    public String username;
    public String email;
    public String password;
    public boolean isActive;

    public User(String username, String email, String password, boolean isActive) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
