package com.order.error;


public class OrderRestException extends OrderException {

    public OrderRestException(HttpStatus httpStatus, String... errors) {
        super(httpStatus, errors);
    }
}

