package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.handlers;

import eu.venthe.pipeline.orchestrator.projects.api.Event;
import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.EventHandler;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class DefaultEventHandler implements EventHandler {

    public Collection<DomainEvent> handle(Project project, Event event) {
        log.warn("Using default event handler for event {}", event);
        throw new UnsupportedOperationException("This event is not handled " + event);
    }
}
