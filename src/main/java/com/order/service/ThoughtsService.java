package com.order.service;

import com.order.model.Thought;
import com.order.repository.ThoughtRepository;

import java.util.List;

public class ThoughtsService {

    private final ThoughtRepository thoughtRepository;

    public ThoughtsService(ThoughtRepository thoughtRepository) {
        this.thoughtRepository = thoughtRepository;
    }

    public List<Thought> getByUserId(long id) {
        return thoughtRepository.getByUserId(id);
    }

    public void create(Thought thought) {
        thoughtRepository.create(thought);
    }
}
