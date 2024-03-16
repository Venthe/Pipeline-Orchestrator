package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers;

import eu.venthe.pipeline.orchestrator.projects.api.version_control.common.EventType;

public interface TypedEventHandler extends EventHandler {
    EventType discriminator();
}
