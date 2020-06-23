package com.order.handler;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.function.Consumer;

public abstract class Handler {

    private static final String TEXT_HTML = "text/html";
    private static final String CONTENT_TYPE = "Content-Type";

    public abstract void register(Javalin lin);

    public void get(String path, Javalin javalin, Consumer<Context> consumer) {
        javalin.get(path, ctx -> {
            ctx.header(CONTENT_TYPE, TEXT_HTML);
            consumer.accept(ctx);
        });
    }
}
