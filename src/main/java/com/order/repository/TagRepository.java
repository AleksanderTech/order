package com.order.repository;

import com.order.database.jooq.tables.records.SortOrderRecord;
import com.order.domain.Tag;
import com.order.model.OrderedTag;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Objects;

import static com.order.database.jooq.Tables.SORT_ORDER;
import static com.order.database.jooq.tables.Tag.TAG;


public class TagRepository {

    private final DSLContext dslContext;

    public TagRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<OrderedTag> orderedTagsByUserId(long userId) {
        return dslContext
                .select(TAG.ID, TAG.NAME, TAG.PARENT_TAG_ID, TAG.CREATED_AT, SORT_ORDER.VALUE)
                .from(TAG)
                .join(SORT_ORDER)
                .on(TAG.SORT_ORDER_ID.eq(SORT_ORDER.ID))
                .where(TAG.PARENT_TAG_ID.isNull())
                .and(TAG.USER_ID.eq(userId))
                .orderBy(SORT_ORDER.VALUE.desc())
                    .fetch(record -> OrderedTag.builder()
                            .id(record.get(TAG.ID))
                            .name(record.get(TAG.NAME))
                            .parentTagId(record.get(TAG.PARENT_TAG_ID))
                            .createdAt(record.get(TAG.CREATED_AT))
                            .orderValue(record.getValue(SORT_ORDER.VALUE))
                            .build());
    }


    public void create(Tag tag) {
        dslContext.transaction(configuration -> {
            Record record = DSL.using(configuration)
                    .select(SORT_ORDER.VALUE)
                    .from(TAG)
                    .join(SORT_ORDER)
                    .on(SORT_ORDER.ID.eq(TAG.SORT_ORDER_ID))
                    .where(TAG.USER_ID.eq(tag.userId))
                    .and(TAG.PARENT_TAG_ID.eq(tag.parentTagId).or(TAG.PARENT_TAG_ID.isNull()))
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
                    .insertInto(TAG, TAG.NAME, TAG.USER_ID, TAG.PARENT_TAG_ID, TAG.SORT_ORDER_ID)
                    .values(tag.name, tag.userId, tag.parentTagId, sortOrderRecord.getId())
                    .execute();
        });
    }
}
