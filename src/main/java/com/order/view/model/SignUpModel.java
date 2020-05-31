package com.order.view.model;

import java.util.List;

public class SignUpModel implements ViewModel<SignUpModel> {

    public List<String> errors;

    public SignUpModel(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public SignUpModel model() {
        return this;
    }
}
