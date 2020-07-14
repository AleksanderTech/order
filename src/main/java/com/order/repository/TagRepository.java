package com.order.repository;

import com.order.database.jooq.tables.records.SortOrderRecord;
import com.order.model.Tag;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import static com.order.database.jooq.Tables.SORT_ORDER;
import static com.order.database.jooq.tables.Tag.TAG;


public class TagRepository {

    private final DSLContext dslContext;

    public TagRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void create(Tag tag) {
        dslContext.transaction(configuration -> {
            SortOrderRecord sortOrderRecord = DSL.using(configuration)
                    .insertInto(SORT_ORDER, SORT_ORDER.VALUE).values(1L).returning()
                    .fetchOne();

            DSL.using(configuration)
                    .insertInto(TAG, TAG.NAME, TAG.USER_ID, TAG.PARENT_TAG_ID, TAG.SORT_ORDER_ID)
                    .values(tag.name, tag.userId, tag.parentTagId, sortOrderRecord.getId())
                    .execute();
        });
    }
}
