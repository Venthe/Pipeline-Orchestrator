package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.handlers;

import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.ProjectEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class DefaultEventHandler extends AbstractEventHandler<ProjectEvent> {
    @Override
    protected Collection<DomainEvent> _handle(Project project, ProjectEvent event) {
        log.warn("Using default event handler for event {}", event);
        throw new UnsupportedOperationException("This event is not handled " + event);
    }
}
