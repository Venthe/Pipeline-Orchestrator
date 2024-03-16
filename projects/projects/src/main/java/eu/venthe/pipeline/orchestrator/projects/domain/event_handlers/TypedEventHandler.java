package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers;

import eu.venthe.pipeline.orchestrator.projects.domain.events.model.EventType;

public interface TypedEventHandler extends EventHandler {
    EventType discriminator();
}
