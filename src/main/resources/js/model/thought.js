export class Thought {

    id;
    name;
    content;
    tagId;
    userId;
    orderValue;
    createdAt;
    modifiedAt;

    constructor(id, name, content, tagId, userId, orderValue, createdAt, modifiedAt) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.tagId = tagId;
        this.userId = userId;
        this.orderValue = orderValue;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}