package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers;

import eu.venthe.pipeline.orchestrator._archive2.events.model.EventType;

public interface TypedEventHandler extends EventHandler {
    EventType discriminator();
}
