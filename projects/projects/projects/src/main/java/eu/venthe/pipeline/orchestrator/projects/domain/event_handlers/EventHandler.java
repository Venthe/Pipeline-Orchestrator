package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers;

import eu.venthe.pipeline.orchestrator.projects.api.Event;
import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;

import java.util.Collection;

public interface EventHandler<T extends Event> {
    Collection<DomainEvent> handle(Project project, T event);

    default boolean canHandle(T event) {
        throw new UnsupportedOperationException();
    }
}
