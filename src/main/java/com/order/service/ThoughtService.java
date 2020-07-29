package com.order.service;

import com.order.entity.ThoughtsViewMetrics;
import com.order.error.Errors;
import com.order.error.HttpStatus;
import com.order.error.OrderException;
import com.order.model.OrderedThought;
import com.order.model.ThoughtRequest;
import com.order.model.ThoughtResponse;
import com.order.model.ThoughtSearch;
import com.order.repository.ThoughtRepository;
import com.order.repository.ThoughtsViewMetricsRepository;

import java.util.List;

public class ThoughtService {

    private final ThoughtRepository thoughtRepository;
    private final ThoughtsViewMetricsRepository viewMetricsRepository;

    public ThoughtService(ThoughtRepository thoughtRepository, ThoughtsViewMetricsRepository viewMetricsRepository) {
        this.thoughtRepository = thoughtRepository;
        this.viewMetricsRepository = viewMetricsRepository;
    }

    public List<OrderedThought> orderedThoughtsBy(long userId, long tagId) {
        return thoughtRepository.orderedThoughtsBy(userId, tagId);
    }

    public List<ThoughtResponse> thoughtsBy(ThoughtSearch thoughtSearch) {
        return thoughtRepository.thoughtsBy(thoughtSearch);
    }

    public void save(ThoughtRequest thought) {
        if (thought.id > 0) {
            thoughtRepository.save(thought);
        } else {
            thoughtRepository.create(thought);
        }
    }

    public ThoughtsViewMetrics viewMetrics(long userId) {
        return viewMetricsRepository.viewMetrics(userId).orElseThrow(() -> new OrderException(HttpStatus.NOT_FOUND, Errors.NOT_FOUND));
    }

    public void saveViewMetrics(long userId, ThoughtsViewMetrics thoughtsViewMetrics) {
        viewMetricsRepository.save(userId, thoughtsViewMetrics);
    }

    public List<ThoughtResponse> findAll(long userId) {
        return thoughtRepository.findAll(userId);
    }

    public void delete(long thoughtId,long userId) {
        thoughtRepository.deleteById(thoughtId,userId);
    }
}
