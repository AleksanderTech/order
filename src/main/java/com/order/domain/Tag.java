package com.order.domain;

import java.time.LocalDateTime;

public class Tag {

    public long id;
    public String name;
    public long userId;
    public long sortOrderId;
    public Long parentTagId;
    public LocalDateTime createdAt;

    public Tag(long id, String name, long userId, long sortOrderId, Long parentTagId, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.sortOrderId = sortOrderId;
        this.parentTagId = parentTagId;
        this.createdAt = createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private long id;
        private String name;
        private long userId;
        public long sortOrderId;
        private Long parentTagId;
        private LocalDateTime createdAt;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder sortOrderValue(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder parentTagId(Long parentTagId) {
            this.parentTagId = parentTagId;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Tag build() {
            return new Tag(this.id, this.name, this.userId,this.sortOrderId, this.parentTagId, this.createdAt);
        }
    }
}
