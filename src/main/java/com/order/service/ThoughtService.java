package com.order.service;

import com.order.model.OrderedThought;
import com.order.model.ThoughtRequest;
import com.order.repository.ThoughtRepository;

import java.util.List;

public class ThoughtService {

    private final ThoughtRepository thoughtRepository;

    public ThoughtService(ThoughtRepository thoughtRepository) {
        this.thoughtRepository = thoughtRepository;
    }

    public List<OrderedThought> orderedThoughtsBy(long userId, long tagId) {
        return thoughtRepository.orderedThoughtsBy(userId,tagId);
    }

    public void create(ThoughtRequest thought) {
        thoughtRepository.create(thought);
    }
}
