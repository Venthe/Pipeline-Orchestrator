package eu.venthe.pipeline.orchestrator._archive2.event_handlers;

import eu.venthe.pipeline.orchestrator._archive2.events.TriggerEvent;

import java.util.Optional;

public interface EventHandler {
    Optional<String> handle(TriggerEvent event);
}
