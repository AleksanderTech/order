package com.order.service;

import java.util.function.Supplier;

public class Service {

    protected <T> Response<T> response(Supplier<T> supplier) {
        try {
            return Response.of(supplier.get());
        } catch (Exception e) {
            // get list of errors from e
            return Response.withErrors("custom error message");
        }
    }
}
