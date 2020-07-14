package com.order.repository;

import com.order.database.jooq.tables.records.SortOrderRecord;
import com.order.database.jooq.tables.records.ThoughtRecord;
import com.order.model.Thought;
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

    public List<Thought> getByUserId(long id) {
        return dslContext.selectFrom(THOUGHT)
                .fetch(thought -> Thought.builder()
                        .id(thought.getId())
                        .name(thought.getName())
                        .content(thought.getContent())
                        .build());
    }

    public void create(Thought thought) {
        // thought must have tag name
//        String thoughtTagName = "stan konta";

        dslContext.transaction(configuration -> {
            Record record = DSL.using(configuration)
                    .select(SORT_ORDER.VALUE)
                    .from(THOUGHT)
                    .join(THOUGHT_TAG)
                    .on(THOUGHT.ID.eq(THOUGHT_TAG.THOUGHT_ID))
                    .join(SORT_ORDER)
                    .on(SORT_ORDER.ID.eq(THOUGHT.SORT_ORDER_ID))
                    .where(THOUGHT_TAG.TAG_NAME.eq(thought.tagName))
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
                    .insertInto(THOUGHT_TAG, THOUGHT_TAG.THOUGHT_ID, THOUGHT_TAG.TAG_NAME)
                    .values(thoughtRecord.getId(), thought.tagName)
                    .execute();
        });
    }
}
