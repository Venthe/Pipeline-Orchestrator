package eu.venthe.pipeline.orchestrator.event_handlers;

import eu.venthe.pipeline.orchestrator.events.TriggerEvent;

import java.util.Optional;

public interface EventHandler {
    Optional<String> handle(TriggerEvent event);
}
