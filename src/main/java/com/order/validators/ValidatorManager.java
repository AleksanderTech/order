package com.order.validators;

import java.util.HashMap;
import java.util.Map;

public class ValidatorManager {

    public Map<String, String> validateUser(String username, String password) {
        Map<String, String> errors = new HashMap<>();

        errors.put(ValidationFields.USERNAME, Validators.username(username));
        errors.put(ValidationFields.PASSWORD, Validators.username(username));
        Validators.password(password);
    }

}
