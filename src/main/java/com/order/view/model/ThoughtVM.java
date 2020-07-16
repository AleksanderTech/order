package com.order.view.model;

import com.order.model.OrderedThought;

import java.util.List;

public class ThoughtVM implements ViewModel<ThoughtVM> {

    public List<OrderedThought> thoughts;

    public ThoughtVM(List<OrderedThought> thoughts) {
        this.thoughts = thoughts;
    }

    @Override
    public ThoughtVM model() {
        return this;
    }
}
