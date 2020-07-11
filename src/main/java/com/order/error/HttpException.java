package com.order.error;

import java.util.List;

public class HttpException extends RuntimeException {

    private int status;
    private List<String> errors;

    public HttpException(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
