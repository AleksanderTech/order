package com.order.view.model;

import java.util.Map;

public class SignUpVM implements ViewModel<SignUpVM> {

    public Map<String, String> errors;

    public SignUpVM(Map<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public SignUpVM model() {
        return this;
    }
}
