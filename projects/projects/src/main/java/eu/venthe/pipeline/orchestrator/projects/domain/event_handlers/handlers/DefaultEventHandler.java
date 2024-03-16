package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.handlers;

import eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.EventHandler;
import eu.venthe.pipeline.orchestrator.projects.domain.events.TriggerEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class DefaultEventHandler implements EventHandler {
    @Override
    public Optional<String> handle(TriggerEvent event) {
        log.warn("Using default event handler for event {}", event);
        throw new UnsupportedOperationException("This event is not handled " + event);
    }
}
