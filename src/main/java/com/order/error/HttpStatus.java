package com.order.error;

public enum HttpStatus {
    NOT_FOUND(404),
    CONFLICT(409),
    INTERNAL_SERVER_ERROR(500),
    UNAUTHORIZED(401),
    OK(200),
    BAD_REQUEST(400);

    private final int statusCode;

    HttpStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
