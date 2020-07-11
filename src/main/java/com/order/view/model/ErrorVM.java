package com.order.view.model;

import java.util.List;

public class ErrorVM implements ViewModel<ErrorVM> {

    public int httpStatus;
    public List<String> errors;

    public ErrorVM(int httpStatus, List<String> errors) {
        this.httpStatus = httpStatus;
        this.errors = errors;
    }

    public ErrorVM(int httpStatus, String... errors) {
        this.httpStatus = httpStatus;
        this.errors = List.of(errors);
    }

    @Override
    public ErrorVM model() {
        return this;
    }
}
