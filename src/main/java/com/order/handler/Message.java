package com.order.handler;

public enum Message {
    ACCOUNT_CREATED("Account has been created");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
