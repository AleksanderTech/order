package com.order.handler;

import io.javalin.Javalin;
import io.javalin.http.Context;


public class ThoughtAndTagHandler extends Handler {

    // input: tag id, output:

    @Override
    public void register(Javalin lin) {
        get("/api/thoughts-and-tags", lin, context -> {
            Long userId = null;
            String userIdName = "userId";
            if (context.sessionAttribute(userIdName) != null) {
                userId = context.sessionAttribute(userIdName);
//                List<Thought> thoughts = ta.getByUserId(userId);
//                context.json(JsonMapper.json(thoughts));
            }
            // todo
        });
    }

    @Override
    public void handle(Context ctx) {

    }
}
