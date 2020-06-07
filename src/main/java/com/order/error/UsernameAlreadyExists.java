package com.order.error;

public class UsernameAlreadyExists extends RuntimeException{

    public UsernameAlreadyExists() {
        super();
    }

    public UsernameAlreadyExists(String message) {
        super(message);
    }
}

