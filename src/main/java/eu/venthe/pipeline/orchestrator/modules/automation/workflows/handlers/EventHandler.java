package eu.venthe.pipeline.orchestrator.modules.automation.workflows.handlers;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectSpecifiedDataProvider;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.ProjectEvent;

import java.util.Collection;

public interface EventHandler<T extends ProjectEvent> {
    Collection<DomainTrigger> handle(T event);

    default boolean canHandle(T event) {
        throw new UnsupportedOperationException();
    }
}
