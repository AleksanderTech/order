package com.order.validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class UserValidator implements Validator {

    private static final Pattern emailPattern = Pattern.compile(("^(.)+[@][^@]+[.][a-zA-Z0-9]+$"));
    private static final Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9]*$");

    @Override
    public List<String> validate(String... value) {
        List<String> errors = new ArrayList<>();
        if (value[0].length() < 2 || !usernamePattern.matcher(value[0]).find()) {
            errors.add(ValidationMessages.USERNAME);
        }
        if (!emailPattern.matcher(value[1]).find()) {
            errors.add(ValidationMessages.EMAIL);
        }
        // todo password
        return errors;
    }
}
