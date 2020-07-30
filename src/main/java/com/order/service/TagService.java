package com.order.service;

import com.order.entity.Tag;
import com.order.error.Errors;
import com.order.error.HttpStatus;
import com.order.error.OrderException;
import com.order.error.OrderRestException;
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

    public void delete(long tagId,long userId) {
        if(tagRepository.hasThoughts(tagId,userId)){
            throw new OrderRestException(HttpStatus.BAD_REQUEST, Errors.CANNOT_DELETE_TAG);
        }
        tagRepository.deleteById(tagId,userId);
    }
}
