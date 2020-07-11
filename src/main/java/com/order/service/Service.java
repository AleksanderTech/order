package com.order.service;

import com.order.error.OrderException;

import java.util.function.Supplier;

public class Service {

    protected <T> Response<T> response(Supplier<T> supplier) {
        try {
            return Response.of(supplier.get());
        } catch (OrderException e) {
            return Response.withErrors(e.getErrors());
        }
    }
}
