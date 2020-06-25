package com.order.repository;

import com.order.model.Thought;
import org.jooq.DSLContext;

import java.util.List;

import static com.order.database.jooq.tables.Thought.THOUGHT;

public class ThoughtsRepository {

    private final DSLContext dslContext;

    public ThoughtsRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<Thought> getByUserId(long id) {
        return dslContext.selectFrom(THOUGHT)
                .fetch(thought -> new Thought(thought.getId(), thought.getContent()));
    }
}
