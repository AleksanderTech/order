package com.order.handler;

import com.order.model.WelcomeModel;
import com.order.view.Presenter;
import com.order.view.Views;
import io.javalin.Javalin;

import java.util.Date;  

public class WelcomeHandler implements Handler {

    private final Presenter presenter;

    public WelcomeHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void register(Javalin lin) {
         lin.get("/", ctx -> {
            ctx.header("Content-Type", "text/html");
            var startModel = new WelcomeModel("name", new Date());
            ctx.result(presenter.template(Views.WELCOME, startModel));
        });
    }
}
