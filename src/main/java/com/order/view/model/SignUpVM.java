package com.order.view.model;

import java.util.Collections;
import java.util.Map;

public class SignUpVM implements ViewModel<SignUpVM> {

    public final Map<String, String> messages;
    public final Map<String, String> errors;

    public SignUpVM(Map<String, String> messages, Map<String, String> errors) {
        this.messages = messages;
        this.errors = errors;
    }

    public static SignUpVM emptyInstance() {
        return new SignUpVM(Collections.emptyMap(), Collections.emptyMap());
    }

    public static SignUpVM withMessages(Map<String, String> messages) {
        return new SignUpVM(messages, Collections.emptyMap());
    }

    public static SignUpVM withErrors(Map<String, String> errors) {
        return new SignUpVM(Collections.emptyMap(), errors);
    }

    @Override
    public SignUpVM model() {
        return this;
    }
}
