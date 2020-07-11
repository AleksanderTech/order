package com.order.error;

import java.util.List;

public class OrderException extends RuntimeException {

    private HttpStatus httpStatus;
    private List<String> errors;

    public OrderException(HttpStatus httpStatus, String... errors) {
        this.httpStatus = httpStatus;
        this.errors = List.of(errors);
    }

    public List<String> getErrors() {
        return errors;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

    public void setStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}

