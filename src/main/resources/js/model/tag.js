export class Tag {

    id;
    name;
    parentTagId;
    orderValue;
    createdAt;

    constructor(id, name, parentTagId, orderValue, createdAt) {
        this.id = id;
        this.name = name;
        this.parentTagId = parentTagId;
        this.orderValue = orderValue;
        this.createdAt = createdAt;
    }
}