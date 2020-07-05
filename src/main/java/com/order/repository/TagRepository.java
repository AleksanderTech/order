package com.order.repository;

import com.order.model.Tag;
import org.jooq.DSLContext;

import static com.order.database.jooq.tables.Tag.TAG;


public class TagRepository {

    private final DSLContext dslContext;

    public TagRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void create(Tag tag) {
        dslContext.insertInto(TAG, TAG.NAME, TAG.USER_ID, TAG.PARENT_TAG_ID)
                .values(tag.name, tag.userId, tag.parentTagId)
                .execute();
    }
}
