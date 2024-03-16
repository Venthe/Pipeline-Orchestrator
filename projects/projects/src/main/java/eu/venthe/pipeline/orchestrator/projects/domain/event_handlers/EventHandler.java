package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers;

import eu.venthe.pipeline.orchestrator.projects.domain.events.TriggerEvent;

import java.util.Optional;

public interface EventHandler {
    Optional<String> handle(TriggerEvent event);
}
