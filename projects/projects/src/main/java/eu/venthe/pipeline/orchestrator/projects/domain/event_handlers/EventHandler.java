package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers;

import eu.venthe.pipeline.orchestrator.projects.api.Event;
import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;

import java.util.Collection;

public interface EventHandler {
    Collection<DomainEvent> handle(Project project, Event event);

    default boolean canHandle(Event event) {
        throw new UnsupportedOperationException();
    }
}
