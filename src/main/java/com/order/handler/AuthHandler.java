package com.order.handler;

import com.order.view.Presenter;
import com.order.view.Views;
import io.javalin.Javalin;

public class AuthHandler implements Handler {

    private Presenter presenter;

    public AuthHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void register(Javalin lin) {
        lin.get("/sign-in", ctx -> {
            ctx.html(presenter.template(Views.SIGN_IN));
        });
        lin.post("/sign-in", ctx -> {
            ctx.result("account created successfully");
        });
    }
}
