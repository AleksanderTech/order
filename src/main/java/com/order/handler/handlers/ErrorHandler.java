package com.order.handler.handlers;

import com.order.error.Errors;
import com.order.error.HttpStatus;
import com.order.error.OrderException;
import com.order.handler.Handler;
import com.order.handler.Routes;
import com.order.view.Presenter;
import com.order.view.Views;
import com.order.view.model.ErrorVM;
import com.order.view.model.SignInVM;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.HashMap;

public class ErrorHandler extends Handler {

    private final Presenter presenter;

    public ErrorHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void register(Javalin lin) {
        lin.error(404, this::error404);
        lin.exception(Exception.class, this::handleException);
        lin.exception(OrderException.class, this::handleOrderException);
        lin.get(Routes.ERROR_ROUTE, this::errorGet);
    }

    public void error404(Context ctx) {
        handleOrderException(new OrderException(HttpStatus.NOT_FOUND, Errors.NOT_FOUND), ctx);
    }

    public void errorGet(Context ctx) {
        ctx.html(presenter.template(Views.ERROR, new SignInVM(new HashMap<>())));
    }

    public void handleException(Exception exception, Context ctx) {
        exception.printStackTrace();
        ctx.status(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());
        ctx.html(presenter.template(Views.ERROR, new ErrorVM(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode(), Errors.INTERNAL_SERVER_ERROR)));
    }

    public void handleOrderException(OrderException orderException, Context ctx) {
        orderException.printStackTrace();
        ctx.status(orderException.getStatus().getStatusCode());
        ctx.html(presenter.template(Views.ERROR, new ErrorVM(orderException.getStatus().getStatusCode(), orderException.getErrors())));
    }
}
