package com.order.handler.handlers;

import com.order.handler.Handler;
import com.order.handler.Routes;
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
        lin.get(Routes.SEARCH_ROUTE, this::searchView);
    }

    public void searchView(Context context) {
        context.html(presenter.template(Views.SEARCH));
    }
}
