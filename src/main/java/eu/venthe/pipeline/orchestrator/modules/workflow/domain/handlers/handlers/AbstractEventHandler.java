package eu.venthe.pipeline.orchestrator.modules.workflow.domain.handlers.handlers;

import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.handlers.EventHandler;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.ProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public abstract class AbstractEventHandler<T extends ProjectEvent> implements EventHandler {
    @Override
    public final Collection<DomainTrigger> handle(ProjectEvent event) {
        log.info("Handling event {} for project {}", event, event.getRepository());
        if (!canHandle(event)) {
            throw new UnsupportedOperationException();
        }

        return _handle((T) event);
    }

    protected abstract Collection<DomainTrigger> _handle(T event);
}
