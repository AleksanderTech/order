package com.order.validators;

public class ValidationMessages {
    public static final String USERNAME = "Username must contain at least 3 characters.";
    public static final String EMAIL = "Invalid email address.";
    public static final String PASSWORD = "Password must contain at least 6 characters, 1 uppercase letter, 1 lowercase letter, 1 special character.";
    public static final String CONFIRMED_PASSWORD = "Password confirmation does not match.";
    public static final String EMPTY = "This field cannot be empty.";
    public static final String EMAIL_EXISTS = "User with provided email already exists.";
    public static final String USERNAME_EXISTS = "User with provided username already exists.";
}
