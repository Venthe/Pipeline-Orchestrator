package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.handlers;

import eu.venthe.pipeline.orchestrator.projects.api.Event;
import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.EventHandler;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public abstract class AbstractEventHandler<T extends Event> implements EventHandler {
    @Override
    public final Collection<DomainEvent> handle(Project project, Event event) {
        log.info("Handling event {} for project {}", event, project.getId());
        if (!canHandle(event)) {
            throw new UnsupportedOperationException();
        }

        return _handle(project, (T) event);
    }

    protected abstract Collection<DomainEvent> _handle(Project project, T event);
}
