package com.order.validators;

import java.util.HashMap;
import java.util.Map;

public class ValidationMessages {

    public static final String USERNAME = "Username must contain at least 3 characters.";
    public static final String EMAIL = "Invalid email address.";
    public static final String PASSWORD = "Password must contain at least 6 characters, 1 uppercase letter, 1 lowercase letter, 1 special character.";
    public static final String CONFIRMED_PASSWORD = "Password confirmation does not match.";
    public static final String EMAIL_EXISTS = "User with provided email already exists.";
    public static final String USERNAME_EXISTS = "User with provided username already exists.";
    public static final String USERNAME_VALIDATION = "Must be more than 3 alphanumeric characters.";
    public static final String PASSWORD_VALIDATION = "Must be more than 8 characters with at least 1 upper case letter, 1 lowercase letter 1 number, and 1 special character.";
    public static final String EMPTY = "This field cannot be empty.";

    public static final Map<ValidationFields, String> MESSAGES = new HashMap<>();

    static {
        MESSAGES.put(ValidationFields.USERNAME, USERNAME_VALIDATION);
        MESSAGES.put(ValidationFields.PASSWORD, USERNAME_VALIDATION);
    }


}
