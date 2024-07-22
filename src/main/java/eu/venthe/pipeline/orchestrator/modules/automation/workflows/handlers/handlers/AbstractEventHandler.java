package eu.venthe.pipeline.orchestrator.modules.automation.workflows.handlers.handlers;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.handlers.EventHandler;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectSpecifiedDataProvider;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.ProjectEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public abstract class AbstractEventHandler<T extends ProjectEvent> implements EventHandler {
    @Override
    public final Collection<DomainTrigger> handle(final ProjectSpecifiedDataProvider provider, ProjectEvent event) {
        log.info("Handling event {} for project {}", event, event.getRepository());
        if (!canHandle(event)) {
            throw new UnsupportedOperationException();
        }

        return _handle(provider, (T) event);
    }

    protected abstract Collection<DomainTrigger> _handle(final ProjectSpecifiedDataProvider provider, T event);
}
