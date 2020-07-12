package com.order.service;

import com.order.error.OrderException;

import java.util.function.Supplier;

public class Service {

    protected <T> Response<T> response(Supplier<T> supplier) {
        try {
            return Response.of(supplier.get());
        } catch (OrderException e) {
            e.printStackTrace();
            return Response.withStatus(e.getStatus(), e.getErrors());
        }
    }

    protected <T> Response<T> response(Runnable action) {
        try {
            action.run();
            return Response.empty();
        } catch (OrderException e) {
            return Response.withStatus(e.getStatus(), e.getErrors());
        }
    }
}
