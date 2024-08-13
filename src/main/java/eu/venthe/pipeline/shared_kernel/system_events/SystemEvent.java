package eu.venthe.pipeline.shared_kernel.system_events;

import eu.venthe.pipeline.shared_kernel.system_events.contexts.UserContext;
import eu.venthe.pipeline.shared_kernel.system_events.model.EventType;

public interface SystemEvent {
    default int getVersion() {
        return 1;
    }

    EventType getType();

    EventId getId();

    UserContext getSender();
}
