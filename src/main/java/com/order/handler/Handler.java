package com.order.handler;

import io.javalin.Javalin;

public interface Handler {

    void register(Javalin lin);
}
