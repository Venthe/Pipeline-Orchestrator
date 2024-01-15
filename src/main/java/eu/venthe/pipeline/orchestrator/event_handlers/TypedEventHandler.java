package eu.venthe.pipeline.orchestrator.event_handlers;

import eu.venthe.pipeline.orchestrator.events.model.EventType;

public interface TypedEventHandler extends EventHandler {
    EventType discriminator();
}
