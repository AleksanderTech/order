package com.order.handler;

import com.order.view.Presenter;
import com.order.view.Views;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class WelcomeHandler extends Handler {

    private final Presenter presenter;

    public WelcomeHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void register(Javalin lin) {
        lin.get(Routes.WELCOME_ROUTE, this::handle);
    }
    @Override
    public void handle(Context ctx) {
        setHeaders(ctx);
        ctx.result(presenter.template(Views.WELCOME));
    }
}
