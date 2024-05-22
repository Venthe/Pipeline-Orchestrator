package eu.venthe.pipeline.orchestrator.projects.domain.projects.events.handlers.handlers;

import eu.venthe.pipeline.orchestrator.projects.domain.projects.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.events.handlers.EventHandler;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public abstract class AbstractEventHandler<T extends SystemEvent> implements EventHandler {
    @Override
    public final Collection<DomainEvent> handle(Project project, SystemEvent event) {
        log.info("Handling event {} for project {}", event, project.getId());
        if (!canHandle(event)) {
            throw new UnsupportedOperationException();
        }

        return _handle(project, (T) event);
    }

    protected abstract Collection<DomainEvent> _handle(Project project, T event);
}
