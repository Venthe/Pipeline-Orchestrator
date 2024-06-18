package eu.venthe.pipeline.orchestrator.modules.workflow.domain.handlers;

import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.ProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

import java.util.Collection;

public interface EventHandler<T extends ProjectEvent> {
    Collection<DomainTrigger> handle(T event);

    default boolean canHandle(T event) {
        throw new UnsupportedOperationException();
    }
}
