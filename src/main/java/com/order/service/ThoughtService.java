package com.order.service;

import com.order.entity.ThoughtsViewMetrics;
import com.order.error.Errors;
import com.order.error.HttpStatus;
import com.order.error.OrderException;
import com.order.model.OrderedThought;
import com.order.model.ThoughtRequest;
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

    public void create(ThoughtRequest thought) {
        thoughtRepository.create(thought);
    }

    public ThoughtsViewMetrics viewMetrics(long userId) {
        return viewMetricsRepository.viewMetrics(userId).orElseThrow(() -> new OrderException(HttpStatus.NOT_FOUND, Errors.NOT_FOUND));
    }

    public void saveViewMetrics(long userId, ThoughtsViewMetrics thoughtsViewMetrics) {
        viewMetricsRepository.save(userId, thoughtsViewMetrics);
    }
}
