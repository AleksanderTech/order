package com.order.error;

public class Errors {
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String EMAIL_EXISTS = "User with provided email already exists.";
    public static final String INCORRECT_PASSWORD = "Incorrect password.";
    public static final String EMAIL = "Invalid email address.";
    public static final String USERNAME = "Username must contain at least 3 alphanumeric characters.";
    public static final String PASSWORD = "Password must contain at least 8 characters, 1 uppercase letter, 1 lowercase letter,1 number, 1 special character.";
    public static final String CONFIRM_PASSWORD = "Password confirmation does not match.";
    public static final String EMPTY = "Cannot be empty.";
}
