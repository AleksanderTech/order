package com.order.validator;

import com.order.error.Errors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Validators {

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String CONFIRM_PASSWORD = "confirm-password";

    private static final String passwordRange = ".{8,30}";
    private static final String minimumOneNumber = ".*[0-9].*";
    private static final String minimumOneUpper = ".*[A-Z].*";
    private static final String minimumOneLower = ".*[a-z].*";
    private static final String minimumOneSpecial = ".*[^A-Za-z0-9].*";
    private static final String emailRegex = "^(.)+[@][^@]+[.][a-zA-Z0-9]+$";
    private static final String usernameRegex = "^[a-zA-Z0-9]{3,}$";
    private static final Pattern emailPattern = Pattern.compile(emailRegex);
    private static final Pattern usernamePattern = Pattern.compile(usernameRegex);
    private static final Pattern[] passwordPatterns = {
            Pattern.compile(passwordRange),
            Pattern.compile(minimumOneNumber),
            Pattern.compile(minimumOneUpper),
            Pattern.compile(minimumOneLower),
            Pattern.compile(minimumOneSpecial)
    };

    public static boolean validUsername(String value) {
        return usernamePattern.matcher(value).matches();
    }

    public static boolean validPassword(String value) {
        return Arrays.stream(passwordPatterns).allMatch(pattern -> pattern.matcher(value).matches());
    }

    public static boolean validEmail(String value) {
        return emailPattern.matcher(value).matches();
    }

    public static boolean validConfirmPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public static boolean isEmpty(String value) {
        return Objects.isNull(value) || value.isBlank();
    }

    public static Map<String, String> signIn(String email, String password) {
        Map<String, String> errors = new HashMap<>();
        applyMessage(email, EMAIL, Errors.EMAIL, errors, Validators::validEmail);
        applyMessage(password, PASSWORD, Errors.PASSWORD, errors, Validators::validPassword);
        return errors;
    }

    public static Map<String, String> signUp(String username, String email, String password, String confirmPassword) {
        Map<String, String> errors = new HashMap<>();
        applyMessage(username, USERNAME, Errors.USERNAME, errors, Validators::validUsername);
        applyMessage(email, EMAIL, Errors.EMAIL, errors, Validators::validEmail);
        applyMessage(password, PASSWORD, Errors.PASSWORD, errors, Validators::validPassword);
        if (!isEmpty(confirmPassword)) {
            if (!Validators.validConfirmPassword(password, confirmPassword)) {
                errors.put(CONFIRM_PASSWORD, Errors.CONFIRM_PASSWORD);
            }
        } else {
            errors.put(CONFIRM_PASSWORD, Errors.EMPTY);
        }
        return errors;
    }

    private static void applyMessage(String value, String field, String message, Map<String, String> errors, Function<String, Boolean> fun) {
        if (!isEmpty(value)) {
            if (!fun.apply(value)) {
                errors.put(field, message);
            }
        } else {
            errors.put(field, Errors.EMPTY);
        }
    }
}
