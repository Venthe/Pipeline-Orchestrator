package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers;

import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.ProjectEvent;

import java.util.Collection;

public interface EventHandler<T extends ProjectEvent> {
    Collection<DomainEvent> handle(Project project, T event);

    default boolean canHandle(T event) {
        throw new UnsupportedOperationException();
    }
}
