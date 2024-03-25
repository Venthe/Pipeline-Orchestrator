package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.EventIdContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.model.EventTypeContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.RepositoryContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.UserContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.function.Function;

// TODO: Add optional enterprise context
// TODO: Add optional installation context
// TODO: Add optional organization context
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class AbstractProjectEvent implements ProjectEvent {
    @ToString.Include
    @Getter(AccessLevel.NONE)
    protected final ObjectNode root;

    @EqualsAndHashCode.Include
    private final UUID id;

    private final EventType type;
    private final RepositoryContext repository;
    /**
     * The user that triggered the event.
     */
    private final UserContext sender;

    protected AbstractProjectEvent(JsonNode _root, EventType type) {
        this(_root);

        if (!getType().equals(type)) {
            throw new IllegalArgumentException(MessageFormat.format("Unsupported event type {0}. Expected {1}", getType(), type));
        }
    }

    public AbstractProjectEvent(JsonNode _root) {
        this.root = ContextUtilities.validateIsObjectNode(_root);

        this.type = EventTypeContext.ensure(root.get("type"));
        this.id = EventIdContext.ensure(root.get("id"));
        this.repository = RepositoryContext.ensure(root.get("repository"));
        this.sender = UserContext.ensure(root.get("sender"));
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
