package eu.venthe.pipeline.orchestrator.projects.domain.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.ProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.model.EventType;
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
