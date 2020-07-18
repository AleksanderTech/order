package com.order.rest.handler;

import com.order.JsonMapper;
import com.order.error.Errors;
import com.order.error.HttpStatus;
import com.order.error.OrderException;
import com.order.handler.Handler;
import com.order.handler.Routes;
import com.order.model.OrderedThought;
import com.order.model.ThoughtRequest;
import com.order.service.ThoughtService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
import java.util.List;

public class ThoughtRestHandler extends Handler {

    private final ThoughtService thoughtService;

    public ThoughtRestHandler(ThoughtService thoughtService) {
        this.thoughtService = thoughtService;
    }

    @Override
    public void register(Javalin lin) {
        lin.get(Routes.API_THOUGHT, this::findThoughts);
        lin.post(Routes.API_THOUGHT, this::createThought);
    }

    public void findThoughts(Context context) {
        Long userId = userId(context).orElseThrow(() -> new OrderException(HttpStatus.UNAUTHORIZED, Errors.UNAUTHORIZED));
        long tagId = context.queryParam("tag-id").length() > 0 ? Long.parseLong(context.queryParam("tag-id")) : -1;
        List<OrderedThought> thoughts = thoughtService.orderedThoughtsBy(userId, tagId);
        context.json(JsonMapper.json(thoughts));
    }

    public void createThought(Context context) {
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
    }
}
