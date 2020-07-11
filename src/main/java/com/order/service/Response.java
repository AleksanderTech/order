package com.order.service;

import java.util.Arrays;
import java.util.List;

public class Response<T> {

    private final T value;
    private final List<String> errors;

    public Response(T value, List<String> errors) {
        this.value = value;
        this.errors = errors;
    }

    public static <T> Response<T> of(T value) {
        return new Response<>(value, null);
    }

    public T getValue() {
        return value;
    }

    public List<String> getErrors() {
        return errors;
    }

    public static <T> Response<T> withErrors(String... errors) {
        return new Response<>(null, Arrays.asList(errors));
    }

    public static <T> Response<T> withErrors(List<String> errors) {
        return new Response<>(null, errors);
    }
}
