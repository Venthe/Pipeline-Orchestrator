import { Observable, Subject } from "rxjs";

export interface Event {
    type: string
    identifier: string
}

export const setupBus = () => {
    const _bus = new Subject<Event>();

    const publish = (...events: Event[]) => {
        events.forEach(event => {
            _bus.next(event)
        })
    }

    const bus: Observable<Event> = _bus

    return {bus, publish, complete: () => _bus.complete()}
}