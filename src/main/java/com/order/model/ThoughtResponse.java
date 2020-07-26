package com.order.model;

import java.time.LocalDateTime;

public class ThoughtResponse {

    public long id;
    public String name;
    public String content;
    public LocalDateTime createdAt;
    public LocalDateTime modifiedAt;

    public ThoughtResponse(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public ThoughtResponse(long id, String name, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.content = content;
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

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder modifiedAt(LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public ThoughtResponse build() {
            return new ThoughtResponse(this.id, this.name, this.content, this.createdAt, this.modifiedAt);
        }
    }
}
