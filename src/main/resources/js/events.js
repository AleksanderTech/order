export class Events {
    subscribers = [];

    subscribe(subscriber) {
        this.subscribers.push(subscriber);
    }

    emit(eventType) {
        this.subscribers.forEach(su => {
            su.update(eventType)
        })
    }
}
export const EVENTS = new Events();