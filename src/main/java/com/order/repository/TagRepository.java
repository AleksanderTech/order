package com.order.repository;

import com.order.database.jooq.tables.records.SortOrderRecord;
import com.order.entity.Tag;
import com.order.model.OrderedTag;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.order.database.jooq.Tables.SORT_ORDER;
import static com.order.database.jooq.Tables.THOUGHT_TAG;
import static com.order.database.jooq.tables.Tag.TAG;
import static com.order.database.jooq.tables.Thought.THOUGHT;


public class TagRepository {

    private final DSLContext dslContext;

    public TagRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<OrderedTag> orderedTagsByUserId(long userId) {
        return tagSortOrderJoin()
                .where(TAG.PARENT_TAG_ID.isNull())
                .and(TAG.USER_ID.eq(userId))
                .orderBy(SORT_ORDER.VALUE.desc())
                .fetch(this::mapToOrderedTag);
    }

    public List<OrderedTag> orderedTagsByUserAndTagId(long userId, long tagId) {
        return tagSortOrderJoin()
                .where(TAG.PARENT_TAG_ID.eq(tagId))
                .and(TAG.USER_ID.eq(userId))
                .orderBy(SORT_ORDER.VALUE.desc())
                .fetch(this::mapToOrderedTag);
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

    private SelectOnConditionStep<Record5<Long, String, Long, LocalDateTime, Long>> tagSortOrderJoin() {
        return dslContext
                .select(TAG.ID, TAG.NAME, TAG.PARENT_TAG_ID, TAG.CREATED_AT, SORT_ORDER.VALUE)
                .from(TAG)
                .join(SORT_ORDER)
                .on(TAG.SORT_ORDER_ID.eq(SORT_ORDER.ID));
    }

    private OrderedTag mapToOrderedTag(Record record) {
        return OrderedTag.builder()
                .id(record.get(TAG.ID))
                .name(record.get(TAG.NAME))
                .parentTagId(record.get(TAG.PARENT_TAG_ID))
                .createdAt(record.get(TAG.CREATED_AT))
                .orderValue(record.getValue(SORT_ORDER.VALUE))
                .build();
    }

    public boolean hasThoughts(long tagId, long userId) {
        return dslContext.fetchExists(dslContext.select(THOUGHT_TAG.TAG_ID)
                .from(THOUGHT_TAG)
                .join(TAG)
                .on(TAG.ID.eq(THOUGHT_TAG.TAG_ID))
                .where(TAG.USER_ID.eq(userId))
                .and(TAG.ID.eq(tagId)));
    }

    public void deleteById(long tagId, long userId) {
        dslContext.deleteFrom(TAG).where(TAG.ID.eq(tagId)).and(TAG.USER_ID.eq(userId)).execute();
    }
}
