package com.order.view.model;

import com.order.model.Thought;

import java.util.List;

public class ThoughtVM implements ViewModel<ThoughtVM> {

    public List<Thought> thoughts;

    public ThoughtVM(List<Thought> thoughts) {
        this.thoughts = thoughts;
    }

    @Override
    public ThoughtVM model() {
        return this;
    }
}
