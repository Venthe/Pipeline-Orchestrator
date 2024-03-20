package eu.venthe.pipeline.orchestrator.projects.domain.events;

import eu.venthe.pipeline.orchestrator.projects.api.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class AbstractEventWrapper<T extends Event> implements EventWrapper<T> {
    private final T event;

    @Override
    public String getId() {
        return event.getId().toString();
    }

    @Override
    public String getType() {
        return event.getType();
    }
}
