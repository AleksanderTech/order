package com.order.handler;

import com.order.model.Thought;
import com.order.service.ThoughtsService;
import com.order.view.Presenter;
import com.order.view.Views;
import io.javalin.Javalin;

import java.util.List;

public class ThoughtsHandler extends Handler {

    private final Presenter presenter;
    private final ThoughtsService thoughtsService;

    public ThoughtsHandler(Presenter presenter, ThoughtsService thoughtsService) {
        this.presenter = presenter;
        this.thoughtsService = thoughtsService;
    }

    @Override
    public void register(Javalin lin) {
        get("/thoughts", lin, context -> {
            Long userId = null;
            String userIdName = "userId";
            if (context.sessionAttribute(userIdName) != null) {
                userId = context.sessionAttribute(userIdName);
                List<Thought> thoughts = thoughtsService.getByUserId(userId);
                context.result(presenter.template(Views.THOUGHTS));
            }
        });
    }
}
