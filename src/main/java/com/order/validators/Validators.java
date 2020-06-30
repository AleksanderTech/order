package com.order.validators;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Validators {

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

    public static boolean username(String value) {
        return usernamePattern.matcher(value).matches();
    }

    public static boolean password(String value) {
        return Arrays.stream(passwordPatterns).allMatch(pattern -> pattern.matcher(value).matches());
    }

    public static boolean email(String value) {
        return emailPattern.matcher(value).matches();
    }

    public static boolean passwordConfirm(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
