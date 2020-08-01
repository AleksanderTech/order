export class Events {
    subscribers = [];

    subscribe(subscriber) {
        this.subscribers.push(subscriber);
    }

    emit(eventType,data) {
        this.subscribers.forEach(su => {
            su.update(eventType,data)
        })
    }
}
export const EVENTS = new Events();
export const TAG_SELECTED = 'TAG_SELECTED';
export const THOUGHT_CREATED = 'THOUGHT_CREATED';