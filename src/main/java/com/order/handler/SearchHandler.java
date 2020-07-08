package com.order.handler;

import com.order.view.Presenter;
import com.order.view.Views;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SearchHandler extends Handler {

    private final Presenter presenter;

    public SearchHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void register(Javalin lin) {
        get("/search", lin, context -> {
            context.result(presenter.template(Views.SEARCH));
        });
    }

}
