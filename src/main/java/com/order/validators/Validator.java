package com.order.validators;

import java.util.List;

public interface Validator {

    List<String> validate(String... value);
}
