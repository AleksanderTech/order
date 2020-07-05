package com.order.service;

import com.order.model.Tag;
import com.order.repository.TagRepository;

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
}
