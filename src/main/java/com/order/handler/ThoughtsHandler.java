package com.order.handler;

import com.order.JsonMapper;
import com.order.model.Thought;
import com.order.service.ThoughtsService;
import com.order.view.Presenter;
import com.order.view.Views;
import com.order.view.model.ThoughtVM;
import io.javalin.Javalin;

import java.io.IOException;
import java.time.LocalDateTime;
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
                    List<Thought> thoughts = thoughtsService.getByUserId(userId);
                    System.out.println(thoughts);
                    context.result(presenter.template(Views.THOUGHTS, new ThoughtVM(thoughts)));
                }
            }
        });

        get("/api/thoughts", lin, context -> {
            Long userId = null;
            String userIdName = "userId";
            if (context.sessionAttribute(userIdName) != null) {
                userId = context.sessionAttribute(userIdName);
                List<Thought> thoughts = thoughtsService.getByUserId(userId);
                context.json(JsonMapper.json(thoughts));
            }
            // todo
        });

        post("/thoughts", lin, context -> {
            try {
                String userIdName = "userId";
                Long userId = null;
                if (context.sessionAttribute(userIdName) != null) {
                    userId = context.sessionAttribute(userIdName);
                    String name = context.formParam("name");
                    thoughtsService.create(Thought.builder().name(name).userId(userId).createdAt(LocalDateTime.now()).build());
                    context.res.sendRedirect("thoughts");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
