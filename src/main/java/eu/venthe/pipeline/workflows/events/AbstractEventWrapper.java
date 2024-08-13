package eu.venthe.pipeline.workflows.events;

import eu.venthe.pipeline.shared_kernel.system_events.SystemEvent;
import eu.venthe.pipeline.shared_kernel.system_events.model.EventType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class AbstractEventWrapper<T extends SystemEvent> implements EventWrapper<T> {
    private final T event;

    @Override
    public String getId() {
        return event.getId().toString();
    }

    @Override
    public EventType getType() {
        return event.getType();
    }
}