package com.order.handler;

import com.order.view.Presenter;
import com.order.view.Views;
import io.javalin.Javalin;

public class WelcomeHandler extends Handler {

    private final Presenter presenter;

    public WelcomeHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void register(Javalin lin) {
        get("/", lin, ctx -> {
            ctx.result(presenter.template(Views.WELCOME));
        });
    }
}
