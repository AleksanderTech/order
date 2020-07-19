package com.order.rest.handler;

import com.order.entity.Tag;
import com.order.handler.Handler;
import com.order.handler.Routes;
import com.order.service.TagService;
import com.order.validator.Validators;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.time.LocalDateTime;

public class TagRestHandler extends Handler {

    private final TagService tagService;

    public TagRestHandler(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public void register(Javalin lin) {
        lin.post(Routes.API_TAG, this::createTag);
        lin.get(Routes.API_TAG, this::findTags);
    }

    public void createTag(Context context) {
        long userId = userId(context);
        String parentTagIdString = context.formParam("parent-tag-id");
        System.out.println("parent tag id: " + parentTagIdString);
        Long parentTagId = Validators.isEmpty(parentTagIdString) ? -1 : Long.parseLong(parentTagIdString);
        String name = context.formParam("name");
        tagService.create(Tag.builder().name(name).userId(userId).parentTagId(parentTagId).createdAt(LocalDateTime.now()).build());
        redirect(context, Routes.THOUGHTS_ROUTE);
    }

    public void findTags(Context context) {
        long userId = userId(context);
        context.json(tagService.orderedTagsByUserId(userId));
    }
}
