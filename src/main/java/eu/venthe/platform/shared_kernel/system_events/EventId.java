package eu.venthe.platform.shared_kernel.system_events;

import java.util.UUID;

public record EventId(UUID value) {
    public String serialize() {
        return value.toString();
    }
}
