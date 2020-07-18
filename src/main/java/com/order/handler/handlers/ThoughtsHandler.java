package com.order.handler.handlers;

import com.order.handler.Handler;
import com.order.handler.Routes;
import com.order.model.OrderedThought;
import com.order.model.ThoughtRequest;
import com.order.service.ThoughtService;
import com.order.view.Presenter;
import com.order.view.Views;
import com.order.view.model.ThoughtVM;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
import java.util.List;

public class ThoughtsHandler extends Handler {

    private final Presenter presenter;
    private final ThoughtService thoughtService;

    public ThoughtsHandler(Presenter presenter, ThoughtService thoughtService) {
        this.presenter = presenter;
        this.thoughtService = thoughtService;
    }

    @Override
    public void register(Javalin lin) {
        lin.get(Routes.THOUGHTS_ROUTE, this::thoughtsTemplate);
    }

    public void thoughtsTemplate(Context context) {
        Long userId;
        String userIdName = "userId";
        if (context.sessionAttribute(userIdName) != null) {
            userId = context.sessionAttribute(userIdName);
            if (userId == null) {
                try {
                    context.res.sendRedirect("error");
                } catch (IOException e) {
                    throw new RuntimeException("redirection failed");
                }
            } else {
                List<OrderedThought> thoughts = thoughtService.orderedThoughtsBy(userId, 1L);
                context.html(presenter.template(Views.THOUGHTS, new ThoughtVM(thoughts)));
            }
        }
    }
}
