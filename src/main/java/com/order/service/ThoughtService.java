package com.order.service;

import com.order.model.Thought;
import com.order.repository.ThoughtRepository;

import java.util.List;

public class ThoughtService {

    private final ThoughtRepository thoughtRepository;

    public ThoughtService(ThoughtRepository thoughtRepository) {
        this.thoughtRepository = thoughtRepository;
    }

    public List<Thought> getByUserId(long id) {
        return thoughtRepository.getByUserId(id);
    }

    public void create(Thought thought) {

        System.out.println(thought);
        thoughtRepository.create(thought);
    }
}
