package com.order.model;

import java.util.List;

public class ThoughtSearch {

    public String name;
    public String content;
    public long userId;
    public List<String> tags;

    public ThoughtSearch() {
    }

    public ThoughtSearch(String name, String content, long userId, List<String> tags) {
        this.name = name;
        this.content = content;
        this.userId = userId;
        this.tags = tags;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String content;
        private long userId;
        public List<String> tags;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public ThoughtSearch build() {
            return new ThoughtSearch(this.name, this.content, this.userId, this.tags);
        }
    }
}
