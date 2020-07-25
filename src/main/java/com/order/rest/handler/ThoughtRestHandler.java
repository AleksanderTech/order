package com.order.rest.handler;

import com.order.JsonMapper;
import com.order.entity.ThoughtsViewMetrics;
import com.order.handler.Handler;
import com.order.handler.Routes;
import com.order.model.OrderedThought;
import com.order.model.ThoughtRequest;
import com.order.service.ThoughtService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class ThoughtRestHandler extends Handler {

    private final ThoughtService thoughtService;

    public ThoughtRestHandler(ThoughtService thoughtService) {
        this.thoughtService = thoughtService;
    }

    @Override
    public void register(Javalin lin) {
        lin.get(Routes.API_THOUGHT, this::findThoughts);
        lin.post(Routes.API_THOUGHT, this::saveThought);
        lin.get(Routes.API_THOUGHT_VIEW_METRICS, this::thoughtsViewMetrics);
        lin.post(Routes.API_THOUGHT_VIEW_METRICS, this::saveThoughtsViewMetrics);
    }

    public void findThoughts(Context context) {
        long userId = userId(context);
        long tagId = Long.parseLong(context.queryParam("tag-id"));
        List<OrderedThought> thoughts = thoughtService.orderedThoughtsBy(userId, tagId);
        context.json(JsonMapper.json(thoughts));
    }

    public void thoughtsViewMetrics(Context context) {
        long userId = userId(context);
        ThoughtsViewMetrics thoughtsViewMetrics = thoughtService.viewMetrics(userId);
        context.json(JsonMapper.json(thoughtsViewMetrics));
    }

    public void saveThoughtsViewMetrics(Context context) {
        long userId = userId(context);
        ThoughtsViewMetrics thoughtsViewMetrics = context.bodyAsClass(ThoughtsViewMetrics.class);
        thoughtService.saveViewMetrics(userId, thoughtsViewMetrics);
    }

    public void saveThought(Context context) {
        long userId = userId(context);
        String name = context.formParam("thought-name");
        long tagId = Long.parseLong(context.formParam("tag-id"));
        thoughtService.create(
                ThoughtRequest.builder()
                        .name(name)
                        .tagId(tagId)
                        .userId(userId)
                        .build()
        );
        redirect(context, "thoughts");
    }
}

