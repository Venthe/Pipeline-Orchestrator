package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.events.EventType;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.events.ProjectEventMarker;

import java.util.UUID;

public interface ProjectEvent<T extends ProjectEventMarker> {
    T getEvent();

    default int getVersion() {
        return 1;
    }

    EventType getEventType();

    UUID
}
