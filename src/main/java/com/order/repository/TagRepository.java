package com.order.repository;

import com.order.database.jooq.tables.records.SortOrderRecord;
import com.order.model.Tag;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.util.Objects;

import static com.order.database.jooq.Tables.SORT_ORDER;
import static com.order.database.jooq.tables.Tag.TAG;


public class TagRepository {

    private final DSLContext dslContext;

    public TagRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void create(Tag tag) {
        dslContext.transaction(configuration -> {
            Record record = DSL.using(configuration)
                    .select(SORT_ORDER.VALUE)
                    .from(TAG)
                    .join(SORT_ORDER)
                    .on(SORT_ORDER.ID.eq(TAG.SORT_ORDER_ID))
                    .where(TAG.PARENT_TAG_ID.eq(tag.parentTagId))
                    .orderBy(SORT_ORDER.VALUE.desc())
                    .limit(1)
                    .fetchOne();
            long orderValue = Objects.isNull(record) ? 1 : record.get(SORT_ORDER.VALUE);
            SortOrderRecord sortOrderRecord = DSL.using(configuration)
                    .insertInto(SORT_ORDER, SORT_ORDER.VALUE)
                    .values(orderValue + 1)
                    .returning()
                    .fetchOne();
            DSL.using(configuration)
                    .insertInto(TAG,TAG.NAME, TAG.USER_ID, TAG.PARENT_TAG_ID, TAG.SORT_ORDER_ID)
                    .values(tag.name, tag.userId,tag.parentTagId, sortOrderRecord.getId())
                    .execute();
        });
    }
}
