package com.order.service;

import com.order.model.Thought;
import com.order.repository.ThoughtsRepository;

import java.util.List;

public class ThoughtsService {

    private final ThoughtsRepository thoughtsRepository;

    public ThoughtsService(ThoughtsRepository thoughtsRepository) {
        this.thoughtsRepository = thoughtsRepository;
    }

    public List<Thought> getByUserId(long id) {
        return thoughtsRepository.getByUserId(id);
    }
}
