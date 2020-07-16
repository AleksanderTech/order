package com.order.model;

import java.time.LocalDateTime;

public class OrderedThought {

    public long id;
    public String name;
    public String content;
    public long tagId;
    public long userId;
    public long orderValue;
    public LocalDateTime createdAt;
    public LocalDateTime modifiedAt;

    public OrderedThought(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public OrderedThought(long id, String name, String content, long tagId, long userId, long orderValue, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.tagId = tagId;
        this.userId = userId;
        this.orderValue = orderValue;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private long id;
        private String name;
        private String content;
        private long tagId;
        private long userId;
        private long orderValue;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder tagId(long tagId) {
            this.tagId = tagId;
            return this;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder orderValue(long orderValue) {
            this.orderValue = orderValue;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder modifiedAt(LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public OrderedThought build() {
            return new OrderedThought(this.id, this.name, this.content, this.tagId, this.userId, this.orderValue, this.createdAt, this.modifiedAt);
        }
    }
}
