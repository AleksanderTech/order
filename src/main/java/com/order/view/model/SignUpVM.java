package com.order.view.model;

import java.util.List;

public class SignUpVM implements ViewModel<SignUpVM> {

    public List<String> errors;

    public SignUpVM(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public SignUpVM model() {
        return this;
    }
}
