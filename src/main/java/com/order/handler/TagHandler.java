package com.order.handler;

import com.order.domain.Tag;
import com.order.service.TagService;
import com.order.validator.Validators;
import com.order.view.Presenter;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
import java.time.LocalDateTime;

public class TagHandler extends Handler {

    private final Presenter presenter;
    private final TagService tagService;

    public TagHandler(Presenter presenter, TagService tagService) {
        this.presenter = presenter;
        this.tagService = tagService;
    }

    @Override
    public void register(Javalin lin) {
        lin.post("/tags", context -> {
            try {
                String userIdName = "userId";
                Long userId = null;
                if (context.sessionAttribute(userIdName) != null) {
                    userId = context.sessionAttribute(userIdName);
                    String parentTagIdString = context.formParam("parent-tag-id");
                    System.out.println("parent tag id: " + parentTagIdString);
                    Long parentTagId = Validators.isEmpty(parentTagIdString) ? -1 : Long.parseLong(parentTagIdString);
                    String name = context.formParam("name");
                    tagService.create(Tag.builder().name(name).userId(userId).parentTagId(parentTagId).createdAt(LocalDateTime.now()).build());
                    context.res.sendRedirect("thoughts");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        lin.get(Routes.TAG_API, this::tagsByUserId);
    }

    public void tagsByUserId(Context context) {
        long  userId = context.sessionAttribute("userId");
        context.json(tagService.orderedTagsByUserId(userId));
    }
}
