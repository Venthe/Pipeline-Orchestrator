package eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.model.EventType;

import java.util.UUID;
import java.util.function.Function;

public interface ProjectEvent {
    ObjectNode getEvent();

    default int getVersion() {
        return 1;
    }

    EventType getType();

    UUID getId();

    <T extends ProjectEvent> T specify(Function<ObjectNode, T> creator);
}
