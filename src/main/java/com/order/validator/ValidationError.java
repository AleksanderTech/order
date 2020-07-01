package com.order.validator;

public class ValidationError {
    public static final String EMAIL = "Invalid email address.";
    public static final String USERNAME = "Username must contain at least 3 alphanumeric characters.";
    public static final String PASSWORD = "Password must contain at least 8 characters, 1 uppercase letter, 1 lowercase letter,1 number, 1 special character.";
    public static final String CONFIRM_PASSWORD = "Password confirmation does not match.";
    public static final String EMPTY = "Cannot be empty.";
}
