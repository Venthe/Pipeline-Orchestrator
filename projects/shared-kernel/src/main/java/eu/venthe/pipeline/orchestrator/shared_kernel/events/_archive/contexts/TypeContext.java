package eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.EventType;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class TypeContext {
    public static Optional<EventType> create(JsonNode root) {
        return ContextUtilities.toTextual(root)
                .flatMap(EventType::of);
    }

    public static EventType ensure(ObjectNode root) {
        return create(root.get("type")).orElseThrow(() -> new IllegalArgumentException("Type must exist"));
    }
}
