package com.order.model;

import java.time.LocalDateTime;

public class Thought {

    public long id;
    public String content;
    public LocalDateTime createdAt;
    public LocalDateTime modifiedAt;

    public Thought(long id, String content) {
        this.id = id;
        this.content = content;
    }
}
