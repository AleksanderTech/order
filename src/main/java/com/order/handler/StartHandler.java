package com.order.handler;

import com.order.model.StartModel;
import com.order.view.Presenter;
import com.order.view.Views;
import io.javalin.Javalin;

import java.util.Date;

public class StartHandler {

    private Presenter presenter;

    public StartHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    public void register(Javalin lin) {
        lin.get("/", ctx -> {
            ctx.header("Content-Type", "text/html");
            var startModel = new StartModel("name", new Date());
            ctx.result(presenter.template(Views.START, startModel));
        });
    }
}
