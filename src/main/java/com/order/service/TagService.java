package com.order.service;

import com.order.entity.Tag;
import com.order.repository.TagRepository;
import com.order.model.OrderedTag;

import java.util.List;

public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void create(Tag tag) {
        if (tag.parentTagId == -1) {
            tag.parentTagId = null;
        }
        tagRepository.create(tag);
    }

    public List<OrderedTag> orderedTagsByUserId(long userId) {
        return tagRepository.orderedTagsByUserId(userId);
    }
}
