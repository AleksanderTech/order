package com.order.view.model;

import java.util.Map;

public class SignInVM implements ViewModel<SignInVM> {

    public Map<String, String> errors;

    public SignInVM(Map<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public SignInVM model() {
        return this;
    }
}
