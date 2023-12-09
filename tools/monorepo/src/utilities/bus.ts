import { Observable, Subject, Subscription, filter } from "rxjs";

export interface Event<T = unknown> {
    payload: T
    type: string
    date: string
}

export const byType = (type: string) =>
    filter<Event>(e => e.type === type)

export const createEvent = <T = unknown>(type: string, payload: T) =>
    ({ type, payload, date: new Date().toUTCString() })

export const setupBus = () => {
    const bus = new Subject<Event>();
    const subscriptions: Subscription[] = []

    return {
        publish: (...events: Event[]) => events.forEach(event => bus.next(event)),
        complete: () => {
            Object.values(subscriptions).forEach(subscription => subscription.unsubscribe())
            return bus.complete();
        },
        subscribe: (subscribeCallback: (observable: Observable<Event>) => Subscription) =>
            subscriptions.push(subscribeCallback(bus))
    }
}