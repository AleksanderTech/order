package com.order.handler;

import com.order.JsonMapper;
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

public class ThoughtHandler extends Handler {

    private final Presenter presenter;
    private final ThoughtService thoughtService;

    public ThoughtHandler(Presenter presenter, ThoughtService thoughtService) {
        this.presenter = presenter;
        this.thoughtService = thoughtService;
    }

    @Override
    public void register(Javalin lin) {
        lin.get("/thoughts", context -> {
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
        });

        lin.post("/thoughts", context -> {
            try {
                String userIdName = "userId";
                Long userId = null;
                if (context.sessionAttribute(userIdName) != null) {
                    userId = context.sessionAttribute(userIdName);
                    if (userId == null) {
                        try {
                            context.res.sendRedirect("error");
                        } catch (IOException e) {
                            throw new RuntimeException("redirection failed");
                        }
                    } else {
                        String name = context.formParam("thought-name");
                        long tagId = Long.parseLong(context.formParam("tag-id"));

                        thoughtService.create(
                                ThoughtRequest.builder()
                                        .name(name)
                                        .tagId(tagId)
                                        .userId(userId)
                                        .build()
                        );
                        context.res.sendRedirect("thoughts");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        lin.get(Routes.THOUGHT_API, this::orderedUserThoughtsByTagId);
    }

    public void orderedUserThoughtsByTagId(Context context) {
        Long userId = null;
        if (context.sessionAttribute("userId") != null) {
            userId = context.sessionAttribute("userId");
            long tagId = context.queryParam("tag-id").length() > 0 ? Long.parseLong(context.queryParam("tag-id")) : -1;
            List<OrderedThought> thoughts = thoughtService.orderedThoughtsBy(userId, tagId);
            context.json(JsonMapper.json(thoughts));
        }
    }
}
