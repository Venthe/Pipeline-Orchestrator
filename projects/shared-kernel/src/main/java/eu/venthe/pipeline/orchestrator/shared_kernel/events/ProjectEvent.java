package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.function.Function;

public interface ProjectEvent {
    ObjectNode getEvent();

    default int getVersion() {
        return 1;
    }

    EventType getType();

    UUID getId();


    ZonedDateTime getTimestamp();

    <T extends ProjectEvent> T specify(Function<ObjectNode, T> creator);
}
