package eu.venthe.pipeline.orchestrator.shared_kernel.system_events;

import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.UserContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.model.EventType;

import java.util.UUID;

public interface SystemEvent {
    default int getVersion() {
        return 1;
    }

    EventType getType();

    EventId getId();

    UserContext getSender();
}
