package com.order.model;

public class ThoughtRequest {

    public long id;
    public String name;
    public String content;
    public long userId;
    public long tagId;

    public ThoughtRequest() {
    }

    public ThoughtRequest(long id, String name, String content, long userId, long tagId) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.userId = userId;
        this.tagId = tagId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private long id;
        private String name;
        private String content;
        private long userId;
        public long tagId;

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


        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder tagId(long tagId) {
            this.tagId = tagId;
            return this;
        }

        public ThoughtRequest build() {
            return new ThoughtRequest(this.id, this.name, this.content, this.userId, this.tagId);
        }
    }

    @Override
    public String toString() {
        return "ThoughtRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", tagId=" + tagId +
                '}';
    }
}
