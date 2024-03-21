package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.EventIdContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.EventTypeContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.function.Function;

@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AbstractProjectEvent implements ProjectEvent {
    @ToString.Include
    protected final ObjectNode root;

    @EqualsAndHashCode.Include
    @Getter
    private final UUID id;

    @EqualsAndHashCode.Include
    @Getter
    private final EventType type;

    protected AbstractProjectEvent(ObjectNode root, EventType type) {
        this.root = root;

        this.type = EventTypeContext.ensure(root.get("type"));
        this.id = EventIdContext.ensure(root.get("id"));

        if (!getType().equals(type)) {
            throw new IllegalArgumentException(MessageFormat.format("Unsupported event type {0}. Expected {1}", getType(), type));
        }
    }

    @Override
    public ObjectNode getEvent() {
        return root;
    }

    @Override
    public <T extends ProjectEvent> T specify(Function<ObjectNode, T> creator) {
        return creator.apply(root);
    }
}
