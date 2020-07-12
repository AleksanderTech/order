package com.order.service;

import com.order.error.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Response<T> {

    private final T value;
    private final HttpStatus httpStatus;
    private final List<String> errors;

    public Response(T value, List<String> errors) {
        this.value = value;
        this.httpStatus = HttpStatus.OK;
        this.errors = errors;
    }

    public Response(T value, HttpStatus httpStatus, List<String> errors) {
        this.value = value;
        this.httpStatus = httpStatus;
        this.errors = errors;
    }

    public T getValue() {
        return value;
    }

    public List<String> getErrors() {
        return errors;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    public static <T> Response<T> of(T value) {
        return new Response<>(value, Collections.emptyList());
    }

    public static <T> Response<T> empty() {
        return new Response<>(null, Collections.emptyList());
    }

    public static <T> Response<T> withErrors(String... errors) {
        return new Response<>(null, Arrays.asList(errors));
    }

    public static <T> Response<T> withErrors(List<String> errors) {
        return new Response<>(null, errors);
    }

    public static <T> Response<T> withStatus(HttpStatus httpStatus, List<String> errors) {
        return new Response<>(null, httpStatus, errors);
    }
}
