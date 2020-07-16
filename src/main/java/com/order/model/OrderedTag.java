package com.order.model;

import java.time.LocalDateTime;

public class OrderedTag {
    public long id;
    public String name;
    public Long parentTagId;
    public Long orderValue;
    public LocalDateTime createdAt;

    public OrderedTag(long id, String name, Long parentTagId, Long orderValue, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.parentTagId = parentTagId;
        this.orderValue = orderValue;
        this.createdAt = createdAt;
    }

    public static OrderedTag.Builder builder() {
        return new OrderedTag.Builder();
    }

    public static class Builder {

        private long id;
        private String name;
        private Long parentTagId;
        private Long orderValue;
        private LocalDateTime createdAt;

        public OrderedTag.Builder id(long id) {
            this.id = id;
            return this;
        }

        public OrderedTag.Builder name(String name) {
            this.name = name;
            return this;
        }

        public OrderedTag.Builder orderValue(Long orderValue) {
            this.orderValue = orderValue;
            return this;
        }

        public OrderedTag.Builder parentTagId(Long parentTagId) {
            this.parentTagId = parentTagId;
            return this;
        }

        public OrderedTag.Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderedTag build() {
            return new OrderedTag(this.id, this.name, this.parentTagId, this.orderValue, this.createdAt);
        }
    }
}
