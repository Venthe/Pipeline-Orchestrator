package eu.venthe.pipeline.orchestrator.modules.workflow.domain.handlers.handlers;

import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.handlers.EventHandler;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public abstract class AbstractEventHandler<T extends SystemEvent> implements EventHandler {
    @Override
    public final Collection<DomainTrigger> handle(Project project, SystemEvent event) {
        log.info("Handling event {} for project {}", event, project.getId());
        if (!canHandle(event)) {
            throw new UnsupportedOperationException();
        }

        return _handle(project, (T) event);
    }

    protected abstract Collection<DomainTrigger> _handle(Project project, T event);
}
