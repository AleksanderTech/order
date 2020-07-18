package com.order.handler.handlers;

import com.order.handler.Handler;
import com.order.handler.Routes;
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
        lin.get(Routes.WELCOME_ROUTE, this::welcome);
    }

    public void welcome(Context ctx) {
        ctx.html(presenter.template(Views.WELCOME));
    }
}
