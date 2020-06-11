package com.order.handler;

import com.order.view.Presenter;
import com.order.view.Views;
import io.javalin.Javalin;

public class ErrorHandler implements Handler {

    private final Presenter presenter;

    public ErrorHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void register(Javalin lin) {
        lin.get("/error", ctx -> {
            ctx.header("Content-Type", "text/html");
            ctx.result(presenter.template(Views.ERROR));
        });
    }
}
