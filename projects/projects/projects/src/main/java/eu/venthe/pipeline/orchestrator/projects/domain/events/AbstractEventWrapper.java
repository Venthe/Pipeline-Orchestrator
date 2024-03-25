package eu.venthe.pipeline.orchestrator.projects.domain.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.ProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class AbstractEventWrapper<T extends ProjectEvent> implements EventWrapper<T> {
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
