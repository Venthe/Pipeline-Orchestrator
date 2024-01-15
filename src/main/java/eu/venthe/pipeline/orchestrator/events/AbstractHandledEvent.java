package eu.venthe.pipeline.orchestrator.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.events.contexts.EventIdContext;
import eu.venthe.pipeline.orchestrator.events.contexts.ActorContext;
import eu.venthe.pipeline.orchestrator.events.contexts.TypeContext;
import eu.venthe.pipeline.orchestrator.events.model.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.text.MessageFormat;
import java.util.UUID;

@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AbstractHandledEvent implements HandledEvent {
    @ToString.Include
    protected final ObjectNode root;

    @EqualsAndHashCode.Include
    @Getter
    private final UUID id;

    @EqualsAndHashCode.Include
    @Getter
    private final EventType type;

    @Getter
    private final ActorContext actor;

    protected AbstractHandledEvent(ObjectNode root, EventType eventType) {
        this.root = root;

        type = TypeContext.ensure(root);
        id = EventIdContext.ensure(root);
        actor = ActorContext.ensure(root.get("actor"));

        if (!getType().equals(eventType)) {
            throw new IllegalArgumentException(MessageFormat.format("Unsupported event type {0}. Expected {1}", getType(), eventType));
        }
    }

    @Override
    public ObjectNode eject() {
        return root;
    }
}
