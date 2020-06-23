package com.order.handler;

import com.order.view.Presenter;
import com.order.view.Views;
import io.javalin.Javalin;

public class ThoughtsHandler extends Handler {

    private final Presenter presenter;

    public ThoughtsHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void register(Javalin lin) {
        get("/thoughts", lin, context -> {
            context.result(presenter.template(Views.THOUGHTS));
        });
    }
}
