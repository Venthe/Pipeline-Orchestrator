package eu.venthe.platform.shared_kernel.system_events;

import eu.venthe.platform.shared_kernel.system_events.contexts.UserContext;
import eu.venthe.platform.shared_kernel.system_events.model.EventType;

import java.time.OffsetDateTime;

public interface SystemEvent {
    default int getVersion() {
        return 1;
    }

    EventType getType();

    EventId getId();

    UserContext getSender();

    OffsetDateTime getTimestamp();
}
