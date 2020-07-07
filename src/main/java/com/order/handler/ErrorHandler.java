package com.order.handler;

import com.order.view.Presenter;
import com.order.view.Views;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class ErrorHandler extends Handler {

    private final Presenter presenter;

    public ErrorHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void register(Javalin lin) {
        get("/error", lin, ctx -> {
            ctx.result(presenter.template(Views.ERROR));
        });
    }

    @Override
    public void handle(Context ctx) {

    }
}
