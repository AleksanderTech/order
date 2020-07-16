package com.order.repository;

import com.order.database.jooq.tables.records.SortOrderRecord;
import com.order.database.jooq.tables.records.ThoughtRecord;
import com.order.model.OrderedThought;
import com.order.model.ThoughtRequest;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Objects;

import static com.order.database.jooq.Tables.SORT_ORDER;
import static com.order.database.jooq.Tables.THOUGHT_TAG;
import static com.order.database.jooq.tables.Thought.THOUGHT;

public class ThoughtRepository {
    private final DSLContext dslContext;

    public ThoughtRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<OrderedThought> orderedThoughtsBy(long userId, long tagId) {
       return dslContext.select(THOUGHT.ID, THOUGHT.NAME, THOUGHT.CONTENT, THOUGHT_TAG.TAG_ID,
                THOUGHT.USER_ID, SORT_ORDER.VALUE, THOUGHT.CREATED_AT, THOUGHT.MODIFIED_AT)
                .from(THOUGHT)
                .join(THOUGHT_TAG)
                .on(THOUGHT.ID.eq(THOUGHT_TAG.THOUGHT_ID))
                .join(SORT_ORDER)
                .on(SORT_ORDER.ID.eq(THOUGHT.SORT_ORDER_ID))
                .where(THOUGHT_TAG.TAG_ID.eq(tagId))
                .and(THOUGHT.USER_ID.eq(userId))
                .orderBy(SORT_ORDER.VALUE.desc())
                .fetch(record -> OrderedThought.builder()
                        .id(record.get(THOUGHT.ID))
                        .name(record.get(THOUGHT.NAME))
                        .content(record.get(THOUGHT.CONTENT))
                        .tagId(record.get(THOUGHT_TAG.TAG_ID))
                        .userId(record.get(THOUGHT.USER_ID))
                        .orderValue(record.getValue(SORT_ORDER.VALUE))
                        .createdAt(record.get(THOUGHT.CREATED_AT))
                        .modifiedAt(record.get(THOUGHT.MODIFIED_AT))
                        .build());
    }

    public void create(ThoughtRequest thought) {
        dslContext.transaction(configuration -> {
            Record record = DSL.using(configuration)
                    .select(SORT_ORDER.VALUE)
                    .from(THOUGHT)
                    .join(THOUGHT_TAG)
                    .on(THOUGHT.ID.eq(THOUGHT_TAG.THOUGHT_ID))
                    .join(SORT_ORDER)
                    .on(SORT_ORDER.ID.eq(THOUGHT.SORT_ORDER_ID))
                    .where(THOUGHT_TAG.TAG_ID.eq(thought.tagId))
                    .orderBy(SORT_ORDER.VALUE.desc())
                    .limit(1)
                    .fetchOne();
            long orderValue = Objects.isNull(record) ? 1 : record.get(SORT_ORDER.VALUE);
            SortOrderRecord sortOrderRecord = DSL.using(configuration)
                    .insertInto(SORT_ORDER, SORT_ORDER.VALUE)
                    .values(orderValue + 1)
                    .returning()
                    .fetchOne();
            ThoughtRecord thoughtRecord = DSL.using(configuration)
                    .insertInto(THOUGHT, THOUGHT.NAME, THOUGHT.CONTENT, THOUGHT.USER_ID, THOUGHT.SORT_ORDER_ID)
                    .values(thought.name, thought.content, thought.userId, sortOrderRecord.getId())
                    .returning()
                    .fetchOne();
            DSL.using(configuration)
                    .insertInto(THOUGHT_TAG, THOUGHT_TAG.THOUGHT_ID, THOUGHT_TAG.TAG_ID)
                    .values(thoughtRecord.getId(), thought.tagId)
                    .execute();
        });
    }
}
