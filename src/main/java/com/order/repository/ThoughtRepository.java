package com.order.repository;

import com.order.model.Thought;
import org.jooq.DSLContext;

import java.util.List;

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
        dslContext.insertInto(THOUGHT, THOUGHT.NAME, THOUGHT.CONTENT, THOUGHT.USER_ID)
                .values(thought.name, thought.content, thought.userId)
                .execute();
    }
}
