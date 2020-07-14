package com.order.model;

import java.time.LocalDateTime;

public class Thought {

    public long id;
    public String name;
    public String content;
    public String tagName;
    public long userId;
    public long sortOrderId;
    public LocalDateTime createdAt;
    public LocalDateTime modifiedAt;

    public Thought(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Thought(long id, String name, String content, String tagName, long userId, long sortOrderId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.tagName = tagName;
        this.userId = userId;
        this.sortOrderId = sortOrderId;
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
        private String tagName;
        private long userId;
        private long sortOrderId;
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

        public Builder tagName(String tagName) {
            this.tagName = tagName;
            return this;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder sortOrderId(long sortOrderId) {
            this.sortOrderId = sortOrderId;
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

        public Thought build() {
            return new Thought(this.id, this.name, this.content, this.tagName, this.userId, this.sortOrderId, this.createdAt, this.modifiedAt);
        }
    }

    @Override
    public String toString() {
        return "Thought{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", tagName='" + tagName + '\'' +
                ", userId=" + userId +
                ", sortOrderId=" + sortOrderId +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
