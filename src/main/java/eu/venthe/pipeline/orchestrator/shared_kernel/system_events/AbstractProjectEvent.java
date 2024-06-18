package eu.venthe.pipeline.orchestrator.shared_kernel.system_events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.EventIdContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.model.EventTypeContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.RepositoryContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.UserContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.model.EventType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.function.Function;

// TODO: Add optional enterprise context
// TODO: Add optional installation context
// TODO: Add optional organization context
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@SuperBuilder
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class AbstractProjectEvent implements SystemEvent, ProjectEvent {
    @EqualsAndHashCode.Include
    private final EventId id;

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
        var root = ContextUtilities.validateIsObjectNode(_root);

        this.type = EventTypeContext.ensure(root.get("type"));
        this.id = EventIdContext.ensure(root.get("id"));
        this.repository = RepositoryContext.ensure(root.get("repository"));
        this.sender = UserContext.ensure(root.get("sender"));
    }
}
