package eu.venthe.pipeline.orchestrator.projects.projects.domain.events.handlers;

import eu.venthe.pipeline.orchestrator.projects.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

import java.util.Collection;

public interface EventHandler<T extends SystemEvent> {
    Collection<DomainTrigger> handle(Project project, T event);

    default boolean canHandle(T event) {
        throw new UnsupportedOperationException();
    }
}
